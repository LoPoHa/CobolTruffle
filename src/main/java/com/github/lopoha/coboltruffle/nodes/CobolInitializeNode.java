package com.github.lopoha.coboltruffle.nodes;

import com.github.lopoha.coboltruffle.nodes.expression.CobolProgramStateNode;
import com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointer;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "initialize", description = "initialize a variable")
public class CobolInitializeNode extends CobolStatementNode {
  private final CobolHeapPointer pointer;

  /**
   * Create a new `initialize` node to reset the value.
   */
  public CobolInitializeNode(CobolHeapPointer pointer) {
    assert pointer != null;
    this.pointer = pointer;
  }


  @Override
  public void executeVoid(VirtualFrame frame) {
    // todo: get state from the frame
    CobolProgramStateNode programState = Helper.getProgramStateFromFrame(frame);
    this.pointer.initialize(programState);
  }
}
