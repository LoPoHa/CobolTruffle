package com.github.lopoha.coboltruffle.heap;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import java.util.List;

public class HeapPointerConst extends HeapPointer {
  /**
   * Create a Pointer to the heap.
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param heap the heap.
   * @param defaultValue the default value.
   */
  protected HeapPointerConst(String name,
                             int position,
                             int length,
                             List<Character> heap,
                             String defaultValue) {
    super(name, position, length, heap, defaultValue);
  }

  /**
   * This is used to for `SET name TO TRUE`.
   */
  @Override
  public void initialize() {
    for (int i = 0; i < this.length; i++) {
      this.heap.set(this.position + i, this.defaultValue.charAt(i));
    }
  }

  @Override
  public void setValue(String value) {
    throw new RuntimeException("Internal error");
  }

  @Override
  public int compareTo(HeapPointer o) {
    throw new RuntimeException("Internal error");
  }

  @TruffleBoundary
  public boolean isSet() {
    return this.defaultValue.equals(this.getValue());
  }
}
