package com.github.lopoha.coboltruffle.heap;

import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.List;

// todo: make class abstract and have multiple implementations: Number, String, ...
// todo: should this class be under nodes/expressions insted of heap?
public class HeapPointer extends CobolExpressionNode {
  final String name;
  public final int position;
  final int length;
  final String defaultValue;
  private final List<Character> heap;

  /**
   * Create a Pointer to the heap.
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param heap the heap.
   * @param defaultValue the default value.
   */
  public HeapPointer(String name,
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
  public void initialize() {
    // is this even possible?
    if (defaultValue != null) {
      this.setValue(this.defaultValue);
    }
  }

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
  public void setValue(String value) {
    assert value != null : "Value required but was null";
    // todo: check if the alignment etc. is correct
    // todo: is the default for number here space or zero?
    if (value.length() < this.length) {
      value = new String(new char[this.length - value.length()]).replace('\0', ' ') + value;
      for (int i = 0; i < value.length(); i++) {
        heap.set(position + i, value.charAt(i));
      }
    } else if (value.length() == this.length) {
      for (int i = 0; i < value.length(); i++) {
        heap.set(position + i, value.charAt(i));
      }
    } else {
      for (int i = 0; i < this.length; i++) {
        heap.set(position + i, value.charAt(i));
      }
    }
  }

  /**
   * Compares the HeapPointer value to another HeapPointer valuie.
   * @param o the other HeapPointer.
   * @return the result of compareTo.
   */
  @TruffleBoundary
  public int compareTo(HeapPointer o) {
    // todo: handle numeric values. how should a number compare to a string.
    //       + handle floats, ...
    return this.getValue().compareTo(o.getValue());
  }


  @Override
  public String executeGeneric(VirtualFrame frame) {
    return this.getValue();
  }
}