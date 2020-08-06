package com.github.lopoha.coboltruffle.nodes.expression.heap;

import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolProgramStateNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// todo: make class abstract and have multiple implementations: Number, String, ...
// todo: should this class be under nodes/expressions insted of heap?
public abstract class CobolHeapPointer extends CobolExpressionNode {
  protected final String name;
  public final int position;
  final int length;
  final List<Character> defaultValue;
  public final int level;

  /**
   * Create a Pointer to the heap.
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param defaultValue the default value.
   * @param level the level of the variable declaration.
   */
  protected CobolHeapPointer(String name,
                             int position,
                             int length,
                             String defaultValue,
                             int level) {
    assert name != null;
    assert position >= 0;
    assert length > 0;
    assert level > 0;

    this.name = name;
    this.position = position;
    this.length = length;
    this.defaultValue = defaultValue == null
                        ? new ArrayList<>()
                        : defaultValue.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
    this.level = level;
  }

  /**
   * Create a Pointer to the heap.
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param defaultValue the default value.
   * @param level the level of the variable declaration.
   */
  protected CobolHeapPointer(String name,
                             int position,
                             int length,
                             List<Character> defaultValue,
                             int level) {
    assert name != null;
    assert position >= 0;
    assert length > 0;
    assert defaultValue != null;
    assert level > 0;

    this.name = name;
    this.position = position;
    this.length = length;
    this.defaultValue = defaultValue;
    this.level = level;
  }

  /**
   * Get a raw point instead of the specialized one.
   * @return the raw pointer.
   */
  public CobolHeapPointerRaw asRawPointer() {
    return new CobolHeapPointerRaw(this.name,
                                   this.position,
                                   this.length,
                                   this.defaultValue,
                                   this.level);
  }

  /**
   * Initialize the variable with the default value.
   */
  public abstract void initialize(CobolProgramStateNode programState);

  /**
   * Get the Value the pointer points to.
   * @return the value.
   */
  public Object getValue(CobolProgramStateNode programState) {
    return programState.getLocalFileHeap().subList(position, position + length);
  }

  /**
   * Set the value at the pointer location.
   * The value is automatically aligned/cut/...
   * @param value the new value
   */
  public void setValue(List<Character> value, CobolProgramStateNode programState) {
    assert value != null : "Value required but was null";
    // todo: check if the alignment etc. is correct
    // todo: is the default for number here space or zero?
    // todo: is parallel always faster?
    if (value.size() < this.length) {
      final String val
          = new String(new char[this.length - value.size()]).replace('\0', ' ') + value;
      IntStream.range(0, value.size())
          .parallel()
          .forEach(i -> programState.getLocalFileHeap().set(position + i, val.charAt(i)));
    } else if (value.size() == this.length) {
      IntStream.range(0, value.size())
          .parallel()
          .forEach(i -> programState.getLocalFileHeap().set(position + i, value.get(i)));
    } else {
      IntStream.range(0, this.length)
          .parallel()
          .forEach(i -> programState.getLocalFileHeap().set(position + i, value.get(i)));
    }
  }
  /**
   * Compares the HeapPointer value to another HeapPointer valuie.
   * @param o the other HeapPointer.
   * @return the result of compareTo.
   */
  @TruffleBoundary
  public abstract int compareTo(CobolHeapPointer o);
}