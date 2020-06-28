package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.heap.HeapPointer;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.CobolInitializeNode;
import com.github.lopoha.coboltruffle.nodes.CobolMoveNode;
import com.github.lopoha.coboltruffle.nodes.CobolRootNode;
import com.github.lopoha.coboltruffle.nodes.CobolStatementNode;
import com.github.lopoha.coboltruffle.nodes.controlflow.CobolBlockNode;
import com.github.lopoha.coboltruffle.nodes.controlflow.CobolSectionBodyNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolFunctionLiteralNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolInvokeNode;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CobolNodeFactory {
  // todo: would it be better to have the first function be added twice to the hashmap, with
  //       different names?
  //       contra: having a normal function call it would cause problems with the initialization...
  private String firstFunctionName;
  private final Map<String, RootCallTarget> allSections = new HashMap<>();
  private String functionName;
  private List<CobolStatementNode> sectionNodes = new ArrayList<>();

  private final CobolLanguage language;

  public CobolNodeFactory(CobolLanguage language) {
    this.language = language;
  }

  /**
   * Create the constructor / entry point of the program.
   * It is added as a function with the name of program to the list.
   * @param programName the name of the program.
   */
  public void createConstructor(String programName) {
    this.functionName = programName.toLowerCase();
    // todo add initializations.

    CobolFunctionLiteralNode firstFunction
        = new CobolFunctionLiteralNode(programName, this.firstFunctionName);

    this.addCall(firstFunction, new ArrayList<>());
    this.finishSection();
  }

  /**
   * Start a new function/section.
   * todo: allow parameters? see https://github.com/LoPoHa/CobolTruffle/issues/14
   *       maybe only for the constructor above.
   * @param functionName the name of the function.
   */
  public void startSection(String programName, String functionName) {
    assert this.functionName == null;
    assert this.sectionNodes.isEmpty();

    this.functionName = CobolNodeFactory.getLocalFunctionName(programName, functionName);
    if (this.firstFunctionName == null) {
      this.firstFunctionName = functionName;
    }
  }

  /**
   * TODO.
   */
  public void finishSection() {
    FrameDescriptor frameDescriptor = new FrameDescriptor();
    final CobolStatementNode sectionBlock
        = new CobolBlockNode(this.sectionNodes.toArray(
                new CobolStatementNode[this.sectionNodes.size()]));
    final CobolSectionBodyNode sectionBody = new CobolSectionBodyNode(sectionBlock);
    final CobolRootNode rootNode
        = new CobolRootNode(language, frameDescriptor, sectionBody, null, this.functionName);
    allSections.put(this.functionName, Truffle.getRuntime().createCallTarget(rootNode));


    this.functionName = null;
    this.sectionNodes = new ArrayList<>();
  }

  public void addMove(String from, HeapPointer to) {
    CobolMoveNode moveNode = new CobolMoveNode(from, to);
    this.sectionNodes.add(moveNode);
  }

  public void addMove(HeapPointer from, HeapPointer to) {
    CobolMoveNode moveNode = new CobolMoveNode(from, to);
    this.sectionNodes.add(moveNode);
  }

  /**
   * todo.
   * @param functionNode the function that should be called.
   * @param parameterNodes the parameters for the function.
   */
  public void addCall(CobolFunctionLiteralNode functionNode,
                      List<CobolExpressionNode> parameterNodes) {
    if (functionNode == null || parameterNodes.contains(null)) {
      // todo: should this fail?
      return;
    }

    final CobolExpressionNode result
        = new CobolInvokeNode(functionNode, parameterNodes.toArray(new CobolExpressionNode[0]));

    result.addExpressionTag();

    this.sectionNodes.add(result);
  }

  public void addInitialize(HeapPointer heapPointer) {
    CobolInitializeNode initializeNode = new CobolInitializeNode(heapPointer);
    this.sectionNodes.add(initializeNode);
  }

  /**
   * Create the function name with the programName as a prefix.
   * DO NOT USE TO CREATE BUILTIN FUNCTION CALLS!
   * @param programName the name of the program.
   * @param functionName the name of the function.
   * @return the function name.
   */
  public static String getLocalFunctionName(String programName, String functionName) {
    return (programName + "." + functionName).toLowerCase();
  }

  public Map<String, RootCallTarget> getAllSections() {
    return this.allSections;
  }
}