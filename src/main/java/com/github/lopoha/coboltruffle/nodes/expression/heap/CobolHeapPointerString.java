package com.github.lopoha.coboltruffle.nodes.expression.heap;

import com.github.lopoha.coboltruffle.nodes.Helper;
import com.github.lopoha.coboltruffle.nodes.expression.CobolProgramStateNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.List;
import java.util.stream.Collectors;

public final class CobolHeapPointerString extends CobolHeapPointer {
  /**
   * Create a Pointer to the heap. The heap must be on the frame.
   *
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param defaultValue the default value.
   * @param level the level of the variable declaration.
   * @param heapName the name of the heap, that is used for the linkage section.
   *                 it is used to get the correct heap when accessing a variable inside.
   */
  public CobolHeapPointerString(
      String name, int position, int length, String defaultValue, int level, String heapName) {
    super(name, position, length, defaultValue, level, heapName);
  }

  @Override
  public void initialize(CobolProgramStateNode programState) {
    // is this even possible?
    if (defaultValue != null) {
      this.setValue(this.defaultValue, programState);
    }
  }

  @Override
  public String getValue(CobolProgramStateNode programState) {
    // safe, since the parent always returns List<Character>
    List<Character> chars = super.getRawValue(programState);
    return chars.stream().map(String::valueOf).collect(Collectors.joining());
  }

  @Override
  public int compareTo(CobolHeapPointer o) {
    // todo
    // return this.getValue(null).compareTo(o.getValue(null));
    return 0;
  }

  @Override
  public String executeGeneric(VirtualFrame frame) {
    return this.getValue(Helper.getProgramStateFromFrame(frame));
  }
}
