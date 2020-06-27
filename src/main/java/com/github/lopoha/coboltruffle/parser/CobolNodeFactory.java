package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.CobolMoveNode;
import com.github.lopoha.coboltruffle.nodes.CobolRootNode;
import com.github.lopoha.coboltruffle.nodes.CobolStatementNode;
import com.github.lopoha.coboltruffle.nodes.controlflow.CobolBlockNode;
import com.github.lopoha.coboltruffle.nodes.controlflow.CobolSectionBodyNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolInvokeNode;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CobolNodeFactory {

  private final Map<String, RootCallTarget> allSections = new HashMap<>();
  private String functionName;
  private final List<CobolStatementNode> sectionNodes = new ArrayList<>();

  private final CobolLanguage language;

  public CobolNodeFactory(CobolLanguage language) {
    this.language = language;
  }

  public void startSection(String functionName) {
    this.functionName = functionName;
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
  }

  /**
   * Todo.
   * @param move the move to add.
   */
  public void addMove(CobolMoveNode move) {
    this.sectionNodes.add(move);
  }

  /**
   * todo.
   * @param functionNode the function that should be called.
   * @param parameterNodes the parameters for the function.
   */
  public void addCall(CobolExpressionNode functionNode,
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

  public Map<String, RootCallTarget> getAllSections() {
    return this.allSections;
  }
}