package com.github.lopoha.coboltruffle.nodes.expression.heap;

import com.github.lopoha.coboltruffle.CobolInternalException;
import com.github.lopoha.coboltruffle.nodes.Helper;
import com.github.lopoha.coboltruffle.nodes.expression.CobolProgramStateNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.List;

public class CobolHeapPointerConst extends CobolHeapPointer {
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
  public CobolHeapPointerConst(
      String name,
      int position,
      int length,
      List<Character> defaultValue,
      int level,
      String heapName) {
    super(name, position, length, defaultValue, level, heapName);
  }

  /** This is used to for `SET name TO TRUE`. */
  @Override
  public void initialize(CobolProgramStateNode programState) {
    for (int i = 0; i < this.length; i++) {
      programState.getLocalFileHeap().set(this.position + i, this.defaultValue.get(i));
    }
  }

  @Override
  public void setValue(List<Character> value, CobolProgramStateNode programState) {
    // todo: this should be checked at "compile time" instead at runtime.
    throw new CobolInternalException();
  }

  @Override
  public int compareTo(CobolHeapPointer o) {
    throw new CobolInternalException();
  }

  @TruffleBoundary
  public boolean isSet() {
    return this.defaultValue.equals(this.getValue(null));
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return this.getValue(Helper.getProgramStateFromFrame(frame));
  }
}
