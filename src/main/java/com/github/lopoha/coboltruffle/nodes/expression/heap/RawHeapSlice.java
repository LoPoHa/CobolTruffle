package com.github.lopoha.coboltruffle.nodes.expression.heap;

import com.oracle.truffle.api.interop.TruffleObject;
import java.util.List;

/**
 * This represents a raw slice of the heap. It is used to wrap it instead of passing around the
 * underlying List directly. Also it is required for call, because List does not implement
 * TruffleObject. And you must implement it, to passing it around when assertions are active.
 */
public class RawHeapSlice implements TruffleObject {
  private final List<Character> heapSlice;

  /**
   * Create a new slice wrapper.
   * @param heapSlice the slice to wrap.
   */
  public RawHeapSlice(List<Character> heapSlice) {
    this.heapSlice = heapSlice;
  }

  public List<Character> getHeapSlice() {
    return heapSlice;
  }
}
