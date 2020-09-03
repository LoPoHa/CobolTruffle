package com.github.lopoha.coboltruffle.nodes.expression.comparison;

import com.github.lopoha.coboltruffle.CobolException;
import com.github.lopoha.coboltruffle.nodes.CobolBinaryNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Node for AND.
 */
@NodeInfo(shortName = "and")
public abstract class CobolAndNode extends CobolBinaryNode {

  @Specialization
  @TruffleBoundary
  protected boolean and(boolean left, boolean right) {
    return left && right;
  }

  @Fallback
  protected Object typeError(Object left, Object right) {
    throw CobolException.typeError(this, left, right);
  }
}
