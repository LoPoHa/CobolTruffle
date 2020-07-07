package com.github.lopoha.coboltruffle.nodes.expression.heap;

import com.github.lopoha.coboltruffle.CobolException;
import com.github.lopoha.coboltruffle.heap.CobolHeap;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.Helper;
import com.github.lopoha.coboltruffle.nodes.expression.CobolProgramStateNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.List;

// todo: make class abstract and have multiple implementations: Number, String, ...
// todo: should this class be under nodes/expressions insted of heap?
public abstract class CobolHeapPointer extends CobolExpressionNode {
  protected final String name;
  public final int position;
  final int length;
  final String defaultValue;

  /**
   * Create a Pointer to the heap.
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param defaultValue the default value.
   */
  protected CobolHeapPointer(String name,
                             int position,
                             int length,
                             String defaultValue) {
    assert name != null;
    assert position >= 0;
    assert length > 0;

    this.name = name;
    this.position = position;
    this.length = length;
    this.defaultValue = defaultValue;
  }

  /**
   * Initialize the variable with the default value.
   */
  public abstract void initialize(CobolProgramStateNode programState);

  /**
   * Get the Value the pointer points to.
   * @return the value.
   */
  public String getValue(CobolProgramStateNode programState) {
    StringBuilder value = new StringBuilder();
    for (int i = position; i < position + length; i++) {
      value.append(programState.getLocalFileHeap().get(i));
    }
    return value.toString();
  }

  /**
   * Set the value at the pointer location.
   * The value is automatically aligned/cut/...
   * @param value the new value
   */
  public abstract void setValue(String value, CobolProgramStateNode programStateNode);

  /**
   * Compares the HeapPointer value to another HeapPointer valuie.
   * @param o the other HeapPointer.
   * @return the result of compareTo.
   */
  @TruffleBoundary
  public abstract int compareTo(CobolHeapPointer o);


  @Override
  public String executeGeneric(VirtualFrame frame) {
    return this.getValue(Helper.getProgramStateFromFrame(frame));
  }
}