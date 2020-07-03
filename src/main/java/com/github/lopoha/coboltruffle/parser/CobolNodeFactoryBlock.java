package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.CobolStatementNode;
import com.github.lopoha.coboltruffle.nodes.controlflow.CobolBlockNode;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.Token;

class CobolNodeFactoryBlock {
  private final Token token;
  private CobolExpressionNode condition;
  private final List<CobolStatementNode> sectionNodes = new ArrayList<>();
  private final CobolNodeFactoryBlock parent;
  private final List<CobolNodeFactoryBlock> childs = new ArrayList<>();

  CobolNodeFactoryBlock(Token token, CobolExpressionNode condition, CobolNodeFactoryBlock parent) {
    this.token = token;
    this.condition = condition;
    this.parent = parent;
    this.parent.addChild(this);
  }

  CobolNodeFactoryBlock(Token token) {
    this.token = token;
    this.parent = null;
  }

  CobolNodeFactoryBlock(Token token, CobolNodeFactoryBlock parent) {
    this.token = token;
    this.parent = parent;
    this.parent.addChild(this);
  }

  private void addChild(CobolNodeFactoryBlock child) {
    assert child != null;
    this.childs.add(child);
  }

  void addStatement(CobolStatementNode node) {
    assert node != null;
    sectionNodes.add(node);
  }

  CobolStatementNode combineBlock() {
    return new CobolBlockNode(
        this.sectionNodes.toArray(new CobolStatementNode[this.sectionNodes.size()]));
  }

  public CobolExpressionNode getCondition() {
    return condition;
  }

  public CobolNodeFactoryBlock getParent() {
    return parent;
  }

  public List<CobolNodeFactoryBlock> getChilds() {
    return childs;
  }
}
