package com.github.lopoha.coboltruffle.nodes.expression.comparison;

import com.github.lopoha.coboltruffle.CobolException;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * A Node to negate the bool.
 */
@NodeChild("value")
@NodeInfo(shortName = "not")
public abstract class CobolNotNode extends CobolExpressionNode {
  @Specialization
  @TruffleBoundary
  protected boolean not(boolean value) {
    return !value;
  }

  @Fallback
  protected Object typeError(Object node) {
    throw CobolException.typeError(this, node);
  }
}
