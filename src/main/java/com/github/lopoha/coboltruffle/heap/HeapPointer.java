package com.github.lopoha.coboltruffle.heap;

import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.List;

// todo: make class abstract and have multiple implementations: Number, String, ...
// todo: should this class be under nodes/expressions insted of heap?
public abstract class HeapPointer extends CobolExpressionNode {
  protected final String name;
  public final int position;
  final int length;
  final String defaultValue;
  protected List<Character> heap = null;

  /**
   * Create a Pointer to the heap.
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param defaultValue the default value.
   */
  protected HeapPointer(String name,
                        int position,
                        int length,
                        String defaultValue) {
    this.name = name;
    this.position = position;
    this.length = length;
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
    if (this.heap == null) {
      List<? extends FrameSlot> slots = frame.getFrameDescriptor().getSlots();

      for (FrameSlot slot : slots) {
        if (slot.getIdentifier().equals(CobolHeap.FRAME_NAME)) {
          try {
            this.heap = (List<Character>) frame.getObject(slot);
          } catch (FrameSlotTypeException e) {
            throw new RuntimeException(e);
          }
          return this.getValue();
        }
      }
    }

    return this.getValue();
  }
}