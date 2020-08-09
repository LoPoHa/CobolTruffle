package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.heap.CobolHeap;
import com.github.lopoha.coboltruffle.nodes.CobolConstructorNode;
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
import com.github.lopoha.coboltruffle.nodes.expression.CobolProgramStateNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolStringLiteralNode;
import com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointer;
import com.github.lopoha.coboltruffle.nodes.local.CobolReadArgumentNode;
import com.github.lopoha.coboltruffle.nodes.local.CobolReadLocalVariableNode;
import com.github.lopoha.coboltruffle.nodes.local.CobolReadLocalVariableNodeGen;
import com.github.lopoha.coboltruffle.nodes.local.CobolWriteLocalVariableNodeGen;
import com.github.lopoha.coboltruffle.runtime.CobolSectionRegistry;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
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
  private RootCallTarget constructor;
  private String functionName;
  private int functionStartPos;
  private CobolNodeFactoryBlock currentBlock;
  private final CobolSectionRegistry fileLocalFunctions;
  private final Source source;
  private FrameDescriptor frameDescriptor;
  private int parameterCount;
  // should this live inseide the CobolNodeFactoryBlock, because of goto?
  private HashMap<String, FrameSlot> frameSlots;


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
  void createConstructor(String programName,
                         Token programExitToken,
                         CobolHeap heap,
                         List<String> inputParameterFileNames) {
    assert this.functionStartPos == 0;
    assert this.functionName == null;
    assert this.currentBlock == null;
    assert this.frameDescriptor == null;
    assert this.parameterCount == 0;
    assert this.frameSlots == null;
    this.functionName = programName;

    this.frameDescriptor = new FrameDescriptor();
    this.frameSlots = new HashMap<>();

    this.currentBlock = new CobolNodeFactoryBlock(programExitToken);
    // todo add initializations.
    //      a new heap should be created and all the nodes should inside be pointed to it.
    //

    FrameSlot frameSlot = frameDescriptor.findOrAddFrameSlot(CobolProgramStateNode.FRAME_NAME,
                                                             this.parameterCount,
                                                             FrameSlotKind.Object);
    this.frameSlots.put(CobolProgramStateNode.FRAME_NAME, frameSlot);

    CobolConstructorNode constructorNode = new CobolConstructorNode(programName,
        heap,
        inputParameterFileNames,
        this.firstFunctionName,
        this.fileLocalFunctions);
    CobolReadLocalVariableNode localVariableNode
        = CobolReadLocalVariableNodeGen.create(frameSlot);
    List<CobolExpressionNode> params = new ArrayList<>();
    params.add(localVariableNode);

    this.addCall(programExitToken, constructorNode, params);

    this.constructor = getCallTarget(programExitToken.getStopIndex());

    this.functionStartPos = 0;
    this.functionName = null;
    this.currentBlock = null;
    this.frameDescriptor = null;
    this.parameterCount = 0;
  }

  private RootCallTarget getCallTarget(int bodyEndPos) {
    final CobolStatementNode sectionBlock = this.currentBlock.combineBlock();
    final CobolSectionBodyNode sectionBody = new CobolSectionBodyNode(sectionBlock);
    final SourceSection functionSrc
        = source.createSection(this.functionStartPos, bodyEndPos - functionStartPos);
    final CobolRootNode rootNode = new CobolRootNode(language,
                                                     this.frameDescriptor,
                                                     sectionBody,
                                                     functionSrc,
                                                     this.functionName);
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
    assert this.frameDescriptor == null;
    assert this.parameterCount == 0;
    assert this.frameSlots == null;

    this.frameDescriptor = new FrameDescriptor();
    this.frameSlots = new HashMap<>();

    this.functionStartPos = token.getStartIndex();
    this.functionName = token.getText();
    if (this.firstFunctionName == null) {
      this.firstFunctionName = functionName;
    }
    this.currentBlock = new CobolNodeFactoryBlock(token);

    this.addLocalHeapParameter();
  }

  void startSection(Token token, String programName) {
    assert this.functionStartPos == 0;
    assert this.functionName == null;
    assert this.currentBlock == null;
    assert this.frameDescriptor == null;
    assert this.parameterCount == 0;
    assert this.frameSlots == null;

    this.frameDescriptor = new FrameDescriptor();
    this.frameSlots = new HashMap<>();

    this.functionStartPos = token.getStartIndex();
    this.functionName = programName;
    this.currentBlock = new CobolNodeFactoryBlock(token);

    this.addLocalHeapParameter();
  }

  /**
   * TODO.
   * @param endNode The node of EXIT.
   */
  void finishSection(Token endNode) {
    this.fileLocalFunctions.register(this.functionName.toLowerCase(),
                                     getCallTarget(endNode.getStopIndex()));

    this.functionStartPos = 0;
    this.functionName = null;
    this.currentBlock = null;
    this.frameDescriptor = null;
    this.parameterCount = 0;
    this.frameSlots = null;
  }

  void addMove(String from, CobolHeapPointer to) {
    CobolMoveNode moveNode = new CobolMoveNode(from, to);
    this.currentBlock.addStatement(moveNode);
  }

  void addMove(CobolHeapPointer from, CobolHeapPointer to) {
    CobolMoveNode moveNode = new CobolMoveNode(from, to);
    this.currentBlock.addStatement(moveNode);
  }

  private void addLocalHeapParameter() {
    assert this.parameterCount == 0;

    final CobolReadArgumentNode readArg = new CobolReadArgumentNode(this.parameterCount);
    final CobolStringLiteralNode stringLiteralNode
        = new CobolStringLiteralNode(CobolProgramStateNode.FRAME_NAME);
    CobolExpressionNode assignment
        = createAssignment(stringLiteralNode, readArg, this.parameterCount);

    this.currentBlock.addStatement(assignment);

    this.parameterCount += 1;

  }

  void addFormalParameter(Token nameToken) {
    final CobolReadArgumentNode readArg = new CobolReadArgumentNode(this.parameterCount);
    CobolExpressionNode assignment
        = createAssignment(createStringLiteral(nameToken, false), readArg, this.parameterCount);

    this.currentBlock.addStatement(assignment);

    this.parameterCount += 1;
  }

  private CobolExpressionNode createAssignment(CobolStringLiteralNode nameNode,
                                               CobolExpressionNode valueNode,
                                               Integer argumentIndex) {
    if (nameNode == null | valueNode == null) {
      return null;
    }

    String name = nameNode.executeGeneric(null);
    FrameSlot frameSlot
        = frameDescriptor.findOrAddFrameSlot(name, argumentIndex, FrameSlotKind.Illegal);
    this.frameSlots.put(name, frameSlot);
    final CobolExpressionNode result
        = CobolWriteLocalVariableNodeGen.create(valueNode, frameSlot, nameNode);

    if (valueNode.hasSource()) {
      final int start = nameNode.getSourceCharIndex();
      final int length = valueNode.getSourceEndIndex() - start;
      result.setSourceSection(start, length);
    }
    result.addExpressionTag();

    return result;
  }

  void addLocalCall(Token callToken, String functionName) {
    CobolFunctionLiteralNode node
        = new CobolLocalFunctionLiteralNode(functionName.toLowerCase(), this.fileLocalFunctions);
    List<CobolExpressionNode> localParams = new ArrayList<>();
    CobolExpressionNode heapParam
        = createRead(callToken, new CobolStringLiteralNode(CobolProgramStateNode.FRAME_NAME));
    localParams.add(heapParam);
    this.addCall(callToken, node, localParams);
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

    final int startPos = callToken.getStartIndex();
    final int endPos = callToken.getStopIndex();
    result.setSourceSection(startPos, endPos - startPos);
    result.addExpressionTag();

    this.currentBlock.addStatement(result);
  }

  void addInitialize(CobolHeapPointer heapPointer) {
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

  private CobolExpressionNode createRead(Token token, CobolStringLiteralNode nameNode) {
    if (nameNode == null) {
      return null;
    }

    String name = nameNode.executeGeneric(null);
    final FrameSlot frameSlot = this.frameSlots.get(name);
    assert frameSlot != null;
    final CobolExpressionNode result = CobolReadLocalVariableNodeGen.create(frameSlot);
    result.setSourceSection(token.getStartIndex(),
                      token.getStopIndex() - token.getStartIndex());
    result.addExpressionTag();
    return result;
  }

  private CobolStringLiteralNode createStringLiteral(Token literalToken, boolean removeQuotes) {
    /* Remove the trailing and ending " */
    String literal = literalToken.getText();
    if (removeQuotes) {
      assert literal.length() >= 2 && literal.startsWith("\"") && literal.endsWith("\"");
      literal = literal.substring(1, literal.length() - 1);
    }

    final CobolStringLiteralNode result = new CobolStringLiteralNode(literal.intern());
    srcFromToken(result, literalToken);
    result.addExpressionTag();
    return result;
  }

  private static void srcFromToken(CobolStatementNode node, Token token) {
    node.setSourceSection(token.getStartIndex(), token.getText().length());
  }

  RootCallTarget getConstructor() {
    return this.constructor;
  }
}