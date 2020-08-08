package com.github.lopoha.coboltruffle.nodes.expression.comparison;

import com.github.lopoha.coboltruffle.CobolException;
import com.github.lopoha.coboltruffle.nodes.CobolBinaryNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * This class is similar to the {@link CobolLessOrEqualNode}.
 */
@NodeInfo(shortName = "<=")
public abstract class CobolLessOrEqualNode extends CobolBinaryNode {

  @Specialization
  @TruffleBoundary
  protected boolean lessEqual(String left, String right) {
    return left.compareTo(right) <= 0;
  }

  @Fallback
  protected Object typeError(Object left, Object right) {
    throw CobolException.typeError(this, left, right);
  }
}
