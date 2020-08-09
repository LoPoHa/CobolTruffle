package com.github.lopoha.coboltruffle.nodes.expression.heap;

import com.github.lopoha.coboltruffle.nodes.Helper;
import com.github.lopoha.coboltruffle.nodes.expression.CobolProgramStateNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.List;

public final class CobolHeapPointerRaw extends CobolHeapPointer {
  /**
   * Create a Pointer to the heap.
   * The heap must be on the frame.
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param defaultValue the default value.
   * @param level the level of the variable declaration.
   */
  public CobolHeapPointerRaw(String name,
                             int position,
                             int length,
                             List<Character> defaultValue,
                             int level) {
    super(name, position, length, defaultValue, level);
  }

  @Override
  public void initialize(CobolProgramStateNode programState) {
    // is this even possible?
    if (defaultValue != null) {
      this.setValue(this.defaultValue, programState);
    }
  }

  @Override
  public int compareTo(CobolHeapPointer o) {
    // todo
    // return this.getValue(null).compareTo(o.getValue(null));
    return 0;
  }

  @Override
  public List<Character> executeGeneric(VirtualFrame frame) {
    return (List<Character>) this.getValue(Helper.getProgramStateFromFrame(frame));
  }
}
