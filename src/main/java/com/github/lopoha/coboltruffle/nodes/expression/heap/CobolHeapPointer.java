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
  public final int position;
  public final int level;
  protected final String name;
  final int length;
  final List<Character> defaultValue;
  private final String heapName;

  /**
   * Create a Pointer to the heap.
   *
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param defaultValue the default value.
   * @param level the level of the variable declaration.
   */
  protected CobolHeapPointer(
      String name, int position, int length, String defaultValue, int level) {
    assert name != null;
    assert position >= 0;
    assert length > 0;
    assert level > 0;

    this.name = name;
    this.position = position;
    this.length = length;
    this.defaultValue =
        defaultValue == null
            ? new ArrayList<>()
            : defaultValue.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
    this.level = level;
    this.heapName = null;
  }

  /**
   * Create a Pointer to the heap.
   *
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param defaultValue the default value.
   * @param level the level of the variable declaration.
   */
  protected CobolHeapPointer(
      String name, int position, int length, List<Character> defaultValue, int level) {
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
    this.heapName = null;
  }

  /**
   * Create a Pointer to the heap.
   *
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param defaultValue the default value.
   * @param level the level of the variable declaration.
   * @param heapName the name of the heap, that is used for the linkage section.
   *                 it is used to get the correct heap when accessing a variable inside.
   */
  protected CobolHeapPointer(
      String name, int position, int length, String defaultValue, int level, String heapName) {
    assert name != null;
    assert position >= 0;
    assert length > 0;
    assert level > 0;

    this.name = name;
    this.position = position;
    this.length = length;
    this.defaultValue =
        defaultValue == null
            ? new ArrayList<>()
            : defaultValue.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
    this.level = level;
    this.heapName = heapName;
  }

  /**
   * Create a Pointer to the heap.
   *
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param defaultValue the default value.
   * @param level the level of the variable declaration.
   * @param heapName the name of the heap, that is used for the linkage section.
   *                 it is used to get the correct heap when accessing a variable inside.
   */
  protected CobolHeapPointer(
      String name,
      int position,
      int length,
      List<Character> defaultValue,
      int level,
      String heapName) {
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
    this.heapName = heapName;
  }

  /**
   * Get a raw point instead of the specialized one.
   *
   * @return the raw pointer.
   */
  public CobolHeapPointerRaw asRawPointer() {
    return new CobolHeapPointerRaw(
        this.name, this.position, this.length, this.defaultValue, this.level);
  }

  /**
   *  Initialize the variable with the default value.
   *
   * @param programState The current state of the program.
   */
  public abstract void initialize(CobolProgramStateNode programState);

  /**
   * Get the Value the pointer points to.
   *
   * @param programState The current state of the program.
   * @return the value.
   */
  public Object getValue(CobolProgramStateNode programState) {
    return getRawValue(programState);
  }

  /**
   * Get the raw heap variable / value.
   *
   * @param programState current program state
   * @return the value.
   */
  public List<Character> getRawValue(CobolProgramStateNode programState) {
    return (this.heapName == null)
        ? programState.getLocalFileHeap().subList(position, position + length)
        : programState
            .getLinkageHeap(this.heapName)
            .getHeapSlice()
            .subList(position, position + length);
  }

  /**
   * Set the value at the pointer location. The value is automatically aligned/cut/...
   *
   * @param value The value to set the variable to.
   * @param programState The current state of the program.
   */
  public void setValue(List<Character> value, CobolProgramStateNode programState) {
    assert value != null : "Value required but was null";

    List<Character> heap =
        (this.heapName == null)
            ? programState.getLocalFileHeap()
            : programState.getLinkageHeap(this.heapName).getHeapSlice();
    // todo: check if the alignment etc. is correct
    // todo: is the default for number here space or zero?
    if (value.size() < this.length) {
      List<Character> newValue = new ArrayList<>();
      for (int i = 0; i < this.length - value.size(); i++) {
        newValue.add(' ');
      }
      newValue.addAll(value);
      IntStream.range(0, this.length)
          .parallel()
          .forEach(i -> heap.set(position + i, newValue.get(i)));
    } else if (value.size() == this.length) {
      IntStream.range(0, value.size())
          .parallel()
          .forEach(i -> heap.set(position + i, value.get(i)));
    } else {
      IntStream.range(0, this.length).parallel().forEach(i -> heap.set(position + i, value.get(i)));
    }
  }

  /**
   * Compares the HeapPointer value to another HeapPointer valuie.
   *
   * @param o the other HeapPointer.
   * @return the result of compareTo.
   */
  @TruffleBoundary
  public abstract int compareTo(CobolHeapPointer o);
}
