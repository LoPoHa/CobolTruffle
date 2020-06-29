package com.github.lopoha.coboltruffle.nodes.expression.comparison;

import com.github.lopoha.coboltruffle.CobolException;
import com.github.lopoha.coboltruffle.heap.HeapPointer;
import com.github.lopoha.coboltruffle.nodes.CobolBinaryNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * This class is similar to the {@link CobolLessThanNode}.
 */
@NodeInfo(shortName = "<=")
public abstract class CobolLessThanNode extends CobolBinaryNode {

  @Specialization
  protected boolean lessOrEqual(long left, long right) {
    return left <= right;
  }

  @Specialization
  @TruffleBoundary
  protected boolean lessThan(HeapPointer left, HeapPointer right) {
    return left.compareTo(right) < 0;
  }

  @Fallback
  protected Object typeError(Object left, Object right) {
    throw CobolException.typeError(this, left, right);
  }
}
