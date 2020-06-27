package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.expression.CobolStringLiteralNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.List;

// todo: make class abstract and have multiple implementations: Number, String, ...
public class HeapPointer extends CobolExpressionNode {
  final String name;
  final int position;
  final int length;
  final String defaultValue;
  private final List<Character> heap;

  HeapPointer(String name, int position, int length, List<Character> heap, String defaultValue) {
    this.name = name;
    this.position = position;
    this.length = length;
    this.heap = heap;
    this.defaultValue = defaultValue;
  }

  void initialize() {
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

  @Override
  public String executeGeneric(VirtualFrame frame) {
    return this.getValue();
  }
}