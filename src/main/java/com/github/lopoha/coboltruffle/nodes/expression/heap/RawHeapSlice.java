package com.github.lopoha.coboltruffle.nodes.expression.heap;

import com.oracle.truffle.api.interop.TruffleObject;

/**
 * This represents a raw slice of the heap. It is used to wrap it instead of passing around the
 * underlying List directly. Also it is required for call, because List does not implement
 * TruffleObject. And you must implement it, to passing it around when assertions are active.
 */
public class RawHeapSlice implements TruffleObject {
  private final char[] heapSlice;

  /**
   * Create a new slice wrapper.
   * @param heapSlice the slice to wrap.
   */
  public RawHeapSlice(char[] heapSlice) {
    this.heapSlice = heapSlice;
  }

  public char[] getHeapSlice() {
    return heapSlice;
  }

  public void setHeapValue(int heapPosition, char[] value) {
    System.arraycopy(value, 0, this.heapSlice, heapPosition, value.length);
  }
}
