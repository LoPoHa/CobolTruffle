package com.github.lopoha.coboltruffle.nodes;

import com.github.lopoha.coboltruffle.heap.HeapPointer;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "initialize", description = "initialize a variable")
public class CobolInitializeNode extends CobolStatementNode {
  private final HeapPointer pointer;

  /**
   * Create a new `initialize` node to reset the value.
   */
  public CobolInitializeNode(HeapPointer pointer) {
    assert pointer != null;
    this.pointer = pointer;
  }


  @Override
  public void executeVoid(VirtualFrame frame) {
    this.pointer.initialize();
  }
}
