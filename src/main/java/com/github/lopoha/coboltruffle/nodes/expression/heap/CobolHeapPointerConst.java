package com.github.lopoha.coboltruffle.nodes.expression.heap;

import com.github.lopoha.coboltruffle.nodes.Helper;
import com.github.lopoha.coboltruffle.nodes.expression.CobolProgramStateNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.frame.VirtualFrame;

import java.util.List;

public class CobolHeapPointerConst extends CobolHeapPointer {
  /**
   * Create a Pointer to the heap.
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param defaultValue the default value.
   * @param level the level of the variable declaration.
   */
  public CobolHeapPointerConst(String name,
                               int position,
                               int length,
                               String defaultValue,
                               int level) {
    super(name, position, length, defaultValue, level);
  }

  /**
   * This is used to for `SET name TO TRUE`.
   */
  @Override
  public void initialize(CobolProgramStateNode programState) {
    for (int i = 0; i < this.length; i++) {
      programState.getLocalFileHeap().set(this.position + i, this.defaultValue.get(i));
    }
  }

  @Override
  public void setValue(List<Character> value, CobolProgramStateNode programState) {
    // todo: this should be checked at "compile time" instead at runtime.
    throw new RuntimeException("Internal error");
  }

  @Override
  public int compareTo(CobolHeapPointer o) {
    throw new RuntimeException("Internal error");
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
