package com.github.lopoha.coboltruffle.nodes;

import com.github.lopoha.coboltruffle.nodes.expression.CobolProgramStateNode;
import com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointer;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "move", description = "set a the value of a variable")
public class CobolMoveNode extends CobolStatementNode {
  private final String value;
  private final CobolHeapPointer from;
  private final CobolHeapPointer target;

  /**
   * Create a new `move` node command with a fixed value.
   * @param value The value to set the target to.
   * @param target Pointer on the heap which value gets changed.
   */
  public CobolMoveNode(String value, CobolHeapPointer target) {
    assert value != null : "value should never be null";
    this.value = value;
    this.from = null;
    this.target = target;
  }

  /**
   * Create a new `move` node command where a value from one part of the heap is copied to another.
   * @param from The position to copy from.
   * @param target The position to copy to.
   */
  public CobolMoveNode(CobolHeapPointer from, CobolHeapPointer target) {
    assert from != null : "HeapPointer 'from' should never be null";
    this.value = null;
    this.from = from;
    this.target = target;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    CobolProgramStateNode programStateNode = Helper.getProgramStateFromFrame(frame);


    if (this.value == null) {
      assert from != null;
      this.target.setValue(from.getValue(programStateNode), programStateNode);
    } else {
      this.target.setValue(this.value, programStateNode);
    }
  }
}
