package com.github.lopoha.coboltruffle.parser.expression;

import com.github.lopoha.coboltruffle.parser.CobolExpressionNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Constant literal for a String value.
 */
@NodeInfo(shortName = "const")
public final class CobolStringLiteralNode extends CobolExpressionNode {

  private final String value;

  public CobolStringLiteralNode(String value) {
    this.value = value;
  }

  @Override
  public String executeGeneric(VirtualFrame frame) {
    return value;
  }
}
