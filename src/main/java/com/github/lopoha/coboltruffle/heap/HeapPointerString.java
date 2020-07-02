package com.github.lopoha.coboltruffle.heap;

import java.util.List;

public final class HeapPointerString extends HeapPointer {
  /**
   * Create a Pointer to the heap.
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param heap the heap.
   * @param defaultValue the default value.
   */
  protected HeapPointerString(String name,
                              int position,
                              int length,
                              List<Character> heap,
                              String defaultValue) {
    super(name, position, length, heap, defaultValue);
  }

  @Override
  public void initialize() {
    // is this even possible?
    if (defaultValue != null) {
      this.setValue(this.defaultValue);
    }
  }

  @Override
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
  public int compareTo(HeapPointer o) {
    return this.getValue().compareTo(o.getValue());
  }
}
