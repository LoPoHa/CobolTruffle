package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.heap.HeapPointer;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.CobolInitializeNode;
import com.github.lopoha.coboltruffle.nodes.CobolMoveNode;
import com.github.lopoha.coboltruffle.nodes.CobolRootNode;
import com.github.lopoha.coboltruffle.nodes.CobolStatementNode;
import com.github.lopoha.coboltruffle.nodes.controlflow.CobolIfNode;
import com.github.lopoha.coboltruffle.nodes.controlflow.CobolSectionBodyNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolFunctionLiteralNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolInvokeNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolLocalFunctionLiteralNode;
import com.github.lopoha.coboltruffle.runtime.CobolSectionRegistry;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.Token;


class CobolNodeFactory {
  // todo: would it be better to have the first function be added twice to the hashmap, with
  //       different names?
  //       contra: having a normal function call it would cause problems with the initialization...
  private String firstFunctionName;
  private final Map<String, RootCallTarget> allSections = new HashMap<>();
  private String functionName;
  private int functionStartPos;
  private CobolNodeFactoryBlock currentBlock;
  private final CobolSectionRegistry fileLocalFunctions;
  private final Source source;


  private final CobolLanguage language;

  CobolNodeFactory(CobolLanguage language, Source source) {
    assert language != null;
    assert source != null;

    this.language = language;
    this.source = source;
    this.fileLocalFunctions = new CobolSectionRegistry(this.language);
  }

  /**
   * Create the constructor / entry point of the program.
   * It is added as a function with the name of program to the list.
   * @param programName the name of the program.
   * @param programExitToken the exit token of the program.
   */
  void createConstructor(String programName, Token programExitToken) {
    assert this.functionStartPos == 0;
    assert this.functionName == null;
    assert this.currentBlock == null;
    this.functionName = programName.toLowerCase();
    // todo: should the start token be given here?
    this.currentBlock = new CobolNodeFactoryBlock(null);
    // todo add initializations.
    //      a new heap should be created and all the nodes should inside be pointed to it.
    //

    CobolFunctionLiteralNode firstFunction
        = new CobolLocalFunctionLiteralNode(this.firstFunctionName, this.fileLocalFunctions);

    this.addCall(programExitToken, firstFunction, new ArrayList<>());

    this.allSections.put(this.functionName, getCallTarget(programExitToken.getStopIndex()));

    this.functionStartPos = 0;
    this.functionName = null;
    this.currentBlock = null;
  }

  private RootCallTarget getCallTarget(int bodyEndPos) {
    FrameDescriptor frameDescriptor = new FrameDescriptor();
    final CobolStatementNode sectionBlock = this.currentBlock.combineBlock();
    final CobolSectionBodyNode sectionBody = new CobolSectionBodyNode(sectionBlock);
    final SourceSection functionSrc
        = source.createSection(this.functionStartPos, bodyEndPos - functionStartPos);
    final CobolRootNode rootNode
        = new CobolRootNode(language, frameDescriptor, sectionBody, functionSrc, this.functionName);
    return Truffle.getRuntime().createCallTarget(rootNode);
  }

  /**
   * Start a new function/section.
   * todo: allow parameters? see https://github.com/LoPoHa/CobolTruffle/issues/14
   *       maybe only for the constructor above.
   * @param token the name of the function declaration.
   */
  void startSection(Token token) {
    assert this.functionStartPos == 0;
    assert this.functionName == null;
    assert this.currentBlock == null;

    this.functionStartPos = token.getStartIndex();
    this.functionName = token.getText();
    if (this.firstFunctionName == null) {
      this.firstFunctionName = functionName;
    }
    this.currentBlock = new CobolNodeFactoryBlock(token);
  }

  /**
   * TODO.
   * @param endNode The node of EXIT.
   */
  void finishSection(Token endNode) {
    this.fileLocalFunctions.register(this.functionName, getCallTarget(endNode.getStopIndex()));

    this.functionStartPos = 0;
    this.functionName = null;
    this.currentBlock = null;
  }

  void addMove(String from, HeapPointer to) {
    CobolMoveNode moveNode = new CobolMoveNode(from, to);
    this.currentBlock.addStatement(moveNode);
  }

  void addMove(HeapPointer from, HeapPointer to) {
    CobolMoveNode moveNode = new CobolMoveNode(from, to);
    this.currentBlock.addStatement(moveNode);
  }

  void addLocalCall(Token callToken, String functionName) {
    CobolFunctionLiteralNode node
        = new CobolLocalFunctionLiteralNode(functionName, this.fileLocalFunctions);
    this.addCall(callToken, node, new ArrayList<>());
  }

  /**
   * todo.
   * @param callToken The token that created the call to this function.
   * @param functionNode the function that should be called.
   * @param parameterNodes the parameters for the function.
   */
  void addCall(Token callToken,
               CobolFunctionLiteralNode functionNode,
               List<CobolExpressionNode> parameterNodes) {
    if (functionNode == null || parameterNodes.contains(null)) {
      // todo: should this fail?
      return;
    }

    final CobolExpressionNode result
        = new CobolInvokeNode(functionNode, parameterNodes.toArray(new CobolExpressionNode[0]));

    /*
    final int startPos = callToken.getStartIndex();
    final int endPos = callToken.getStopIndex();
    result.setSourceSection(startPos, endPos - startPos);
    */
    result.addExpressionTag();

    this.currentBlock.addStatement(result);
  }

  void addInitialize(HeapPointer heapPointer) {
    CobolInitializeNode initializeNode = new CobolInitializeNode(heapPointer);
    this.currentBlock.addStatement(initializeNode);
  }

  /**
   * Start the if block and sets the current block to the `true` block.
   * @param ifToken the token that started the if
   * @param trueBranchToken the token that started the true branch
   * @param condition the condition for the if.
   */
  void startIf(Token ifToken, Token trueBranchToken, CobolExpressionNode condition) {
    CobolNodeFactoryBlock ifNode = new CobolNodeFactoryBlock(ifToken, condition, this.currentBlock);
    this.currentBlock = new CobolNodeFactoryBlock(trueBranchToken, ifNode);
  }

  /**
   * Switch the current block to the else branch (the second child of the current parent block).
   * @param elseBranchToken the token that started the else branch
   */
  void elseIf(Token elseBranchToken) {
    this.currentBlock = new CobolNodeFactoryBlock(elseBranchToken, this.currentBlock.getParent());
  }

  /**
   * end the if, that is currently open.
   * add the if as a statement to the statements of the parent.
   */
  void endIf() {
    CobolNodeFactoryBlock ifNode = this.currentBlock.getParent();
    assert ifNode != null;
    CobolStatementNode trueBlock = ifNode.getChilds().get(0).combineBlock();
    CobolStatementNode elseBlock = ifNode.getChilds().size() > 1
        ? ifNode.getChilds().get(1).combineBlock()
        : null;
    this.currentBlock = ifNode.getParent();
    assert this.currentBlock != null;
    this.currentBlock.addStatement(new CobolIfNode(ifNode.getCondition(), trueBlock, elseBlock));

    // todo: is this correct?
    assert this.currentBlock != null;
    this.currentBlock.getChilds().remove(this.currentBlock.getChilds().size() - 1);
  }

  Map<String, RootCallTarget> getAllSections() {
    return this.allSections;
  }
}