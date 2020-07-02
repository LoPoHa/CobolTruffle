package com.github.lopoha.coboltruffle.heap;

import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.List;

// todo: make class abstract and have multiple implementations: Number, String, ...
// todo: should this class be under nodes/expressions insted of heap?
public abstract class HeapPointer extends CobolExpressionNode {
  protected final String name;
  public final int position;
  final int length;
  final String defaultValue;
  protected final List<Character> heap;

  /**
   * Create a Pointer to the heap.
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param heap the heap.
   * @param defaultValue the default value.
   */
  protected HeapPointer(String name,
                        int position,
                        int length,
                        List<Character> heap,
                        String defaultValue) {
    this.name = name;
    this.position = position;
    this.length = length;
    this.heap = heap;
    this.defaultValue = defaultValue;
  }

  /**
   * Initialize the variable with the default value.
   */
  public abstract void initialize();

  /**
   * Get the Value the pointer points to.
   * @return the value.
   */
  public String getValue() {
    StringBuilder value = new StringBuilder();
    for (int i = position; i < position + length; i++) {
      value.append(heap.get(i));
    }
    return value.toString();
  }

  /**
   * Set the value at the pointer location.
   * The value is automatically aligned/cut/...
   * @param value the new value
   */
  public abstract void setValue(String value);

  /**
   * Compares the HeapPointer value to another HeapPointer valuie.
   * @param o the other HeapPointer.
   * @return the result of compareTo.
   */
  @TruffleBoundary
  public abstract int compareTo(HeapPointer o);


  @Override
  public String executeGeneric(VirtualFrame frame) {
    return this.getValue();
  }
}