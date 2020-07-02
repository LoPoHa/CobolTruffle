package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.heap.HeapPointer;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.CobolInitializeNode;
import com.github.lopoha.coboltruffle.nodes.CobolMoveNode;
import com.github.lopoha.coboltruffle.nodes.CobolRootNode;
import com.github.lopoha.coboltruffle.nodes.CobolStatementNode;
import com.github.lopoha.coboltruffle.nodes.controlflow.CobolBlockNode;
import com.github.lopoha.coboltruffle.nodes.controlflow.CobolIfNode;
import com.github.lopoha.coboltruffle.nodes.controlflow.CobolSectionBodyNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolFunctionLiteralNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolInvokeNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolLocalFunctionLiteralNode;
import com.github.lopoha.coboltruffle.runtime.CobolSectionRegistry;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class CobolNodeFactory {
  // todo: would it be better to have the first function be added twice to the hashmap, with
  //       different names?
  //       contra: having a normal function call it would cause problems with the initialization...
  private String firstFunctionName;
  private final Map<String, RootCallTarget> allSections = new HashMap<>();
  private String functionName;
  private CobolBlock currentBlock;
  private final CobolSectionRegistry fileLocalFunctions;

  private class CobolBlock {
    private CobolExpressionNode condition;
    private final List<CobolStatementNode> sectionNodes = new ArrayList<>();
    private final CobolBlock parent;
    private final List<CobolBlock> childs = new ArrayList<>();

    private CobolBlock(CobolExpressionNode condition, CobolBlock parent) {
      this.condition = condition;
      this.parent = parent;
      this.parent.addChild(this);
    }

    private CobolBlock() {
      this.parent = null;
    }

    private CobolBlock(CobolBlock parent) {
      this.parent = parent;
      this.parent.addChild(this);
    }

    private void addChild(CobolBlock child) {
      assert child != null;
      this.childs.add(child);
    }

    private void addStatement(CobolStatementNode node) {
      assert node != null;
      sectionNodes.add(node);
    }

    private CobolStatementNode combineBlock() {
      return new CobolBlockNode(
          this.sectionNodes.toArray(new CobolStatementNode[this.sectionNodes.size()]));
    }

  }

  private final CobolLanguage language;

  CobolNodeFactory(CobolLanguage language) {
    this.language = language;
    this.fileLocalFunctions = new CobolSectionRegistry(this.language);
  }

  /**
   * Create the constructor / entry point of the program.
   * It is added as a function with the name of program to the list.
   * @param programName the name of the program.
   */
  void createConstructor(String programName) {
    assert this.functionName == null;
    assert this.currentBlock == null;
    this.functionName = programName.toLowerCase();
    this.currentBlock = new CobolBlock();
    // todo add initializations.
    //      a new heap should be created and all the nodes should inside be pointed to it.
    //

    CobolFunctionLiteralNode firstFunction
        = new CobolLocalFunctionLiteralNode(this.firstFunctionName, this.fileLocalFunctions);

    this.addCall(firstFunction, new ArrayList<>());

    this.allSections.put(this.functionName, getCallTarget());

    this.functionName = null;
    this.currentBlock = null;
  }

  private RootCallTarget getCallTarget() {
    FrameDescriptor frameDescriptor = new FrameDescriptor();
    final CobolStatementNode sectionBlock = this.currentBlock.combineBlock();
    final CobolSectionBodyNode sectionBody = new CobolSectionBodyNode(sectionBlock);
    final CobolRootNode rootNode
        = new CobolRootNode(language, frameDescriptor, sectionBody, null, this.functionName);
    return Truffle.getRuntime().createCallTarget(rootNode);
  }

  /**
   * Start a new function/section.
   * todo: allow parameters? see https://github.com/LoPoHa/CobolTruffle/issues/14
   *       maybe only for the constructor above.
   * @param functionName the name of the function.
   */
  void startSection(String functionName) {
    assert this.functionName == null;
    assert this.currentBlock == null;

    this.functionName = functionName;
    if (this.firstFunctionName == null) {
      this.firstFunctionName = functionName;
    }
    this.currentBlock = new CobolBlock();
  }

  /**
   * TODO.
   */
  void finishSection() {
    this.fileLocalFunctions.register(this.functionName, getCallTarget());
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

  void addLocalCall(String functionName) {
    CobolFunctionLiteralNode node
        = new CobolLocalFunctionLiteralNode(functionName, this.fileLocalFunctions);
    this.addCall(node, new ArrayList<>());
  }

  /**
   * todo.
   * @param functionNode the function that should be called.
   * @param parameterNodes the parameters for the function.
   */
  void addCall(CobolFunctionLiteralNode functionNode,
               List<CobolExpressionNode> parameterNodes) {
    if (functionNode == null || parameterNodes.contains(null)) {
      // todo: should this fail?
      return;
    }

    final CobolExpressionNode result
        = new CobolInvokeNode(functionNode, parameterNodes.toArray(new CobolExpressionNode[0]));

    result.addExpressionTag();

    this.currentBlock.addStatement(result);
  }

  void addInitialize(HeapPointer heapPointer) {
    CobolInitializeNode initializeNode = new CobolInitializeNode(heapPointer);
    this.currentBlock.addStatement(initializeNode);
  }

  /**
   * Start the if block and sets the current block to the `true` block.
   * @param condition the condition for the if.
   */
  void startIf(CobolExpressionNode condition) {
    CobolBlock ifNode = new CobolBlock(condition, this.currentBlock);
    CobolBlock trueBranch = new CobolBlock(ifNode);
    this.currentBlock = trueBranch;
  }

  /**
   * Switch the current block to the else branch (the second child of the current parent block).
   */
  void elseIf() {
    CobolBlock elseBranch = new CobolBlock(this.currentBlock.parent);
    this.currentBlock = elseBranch;
  }

  /**
   * end the if, that is currently open.
   * add the if as a statement to the statements of the parent.
   */
  void endIf() {
    CobolBlock ifNode = this.currentBlock.parent;
    assert ifNode != null;
    CobolStatementNode trueBlock = ifNode.childs.get(0).combineBlock();
    CobolStatementNode elseBlock = ifNode.childs.size() > 1
        ? ifNode.childs.get(1).combineBlock()
        : null;
    this.currentBlock = ifNode.parent;
    assert this.currentBlock != null;
    this.currentBlock.addStatement(new CobolIfNode(ifNode.condition, trueBlock, elseBlock));

    // todo: is this correct?
    assert this.currentBlock != null;
    this.currentBlock.childs.remove(this.currentBlock.childs.size() - 1);
  }

  Map<String, RootCallTarget> getAllSections() {
    return this.allSections;
  }
}