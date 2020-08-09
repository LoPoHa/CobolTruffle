package com.github.lopoha.coboltruffle.nodes;

import com.github.lopoha.coboltruffle.nodes.expression.CobolProgramStateNode;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.List;

public class Helper {

  /**
   * get the program state from the given frame.
   * @param frame the frame.
   * @return the program state
   */
  public static CobolProgramStateNode getProgramStateFromFrame(VirtualFrame frame) {
    List<? extends FrameSlot> slots = frame.getFrameDescriptor().getSlots();

    for (FrameSlot slot : slots) {
      if (slot.getIdentifier().equals(CobolProgramStateNode.FRAME_NAME)) {
        try {
          return (CobolProgramStateNode) frame.getObject(slot);
        } catch (FrameSlotTypeException e) {
          throw new RuntimeException(e);
        }
      }
    }
    throw new RuntimeException("internal error");
  }
}
