package com.github.lopoha.coboltruffle.nodes.expression.comparison;

import com.github.lopoha.coboltruffle.CobolException;
import com.github.lopoha.coboltruffle.nodes.CobolBinaryNode;
import com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointer;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * This class is similar to the {@link CobolEqualNode}.
 */
@NodeInfo(shortName = "=")
public abstract class CobolEqualNode extends CobolBinaryNode {

  @Specialization
  @TruffleBoundary
  protected boolean equal(CobolHeapPointer left, CobolHeapPointer right) {
    return left.compareTo(right) <= 0;
  }

  @Specialization
  @TruffleBoundary
  protected boolean equal(String left, CobolHeapPointer right) {
    return left.compareTo(right.getValue(null)) <= 0;
  }

  @Specialization
  @TruffleBoundary
  protected boolean equal(CobolHeapPointer left, String right) {
    return left.getValue(null).compareTo(right) <= 0;
  }

  @Specialization
  @TruffleBoundary
  protected boolean equal(String left, String right) {
    return left.compareTo(right) <= 0;
  }

  @Fallback
  protected Object typeError(Object left, Object right) {
    throw CobolException.typeError(this, left, right);
  }
}
