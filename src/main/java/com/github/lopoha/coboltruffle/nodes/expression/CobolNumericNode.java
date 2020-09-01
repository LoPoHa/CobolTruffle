package com.github.lopoha.coboltruffle.nodes.expression;

import com.github.lopoha.coboltruffle.CobolException;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * A Node to check if the value is Numeric.
 */
@NodeChild("node")
@NodeInfo(shortName = "numeric")
public abstract class CobolNumericNode extends CobolExpressionNode {
  @Specialization
  @TruffleBoundary
  protected boolean isNumeric(String node) {
    try {
      Long.parseLong(node);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  @Fallback
  protected Object typeError(Object node) {
    throw CobolException.typeError(this, node);
  }
}
