package com.github.lopoha.coboltruffle.nodes.expression.heap;

import com.github.lopoha.coboltruffle.nodes.expression.CobolProgramStateNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;

public class CobolHeapPointerConst extends CobolHeapPointer {
  /**
   * Create a Pointer to the heap.
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param defaultValue the default value.
   */
  public CobolHeapPointerConst(String name,
                                  int position,
                                  int length,
                                  String defaultValue) {
    super(name, position, length, defaultValue);
  }

  /**
   * This is used to for `SET name TO TRUE`.
   */
  @Override
  public void initialize(CobolProgramStateNode programState) {
    for (int i = 0; i < this.length; i++) {
      programState.getLocalFileHeap().set(this.position + i, this.defaultValue.charAt(i));
    }
  }

  @Override
  public void setValue(String value, CobolProgramStateNode programState) {
    throw new RuntimeException("Internal error");
  }

  @Override
  public int compareTo(CobolHeapPointer o) {
    throw new RuntimeException("Internal error");
  }

  @TruffleBoundary
  public boolean isSet() {
    return this.defaultValue.equals(this.getValue(null));
  }
}
