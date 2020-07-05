package com.github.lopoha.coboltruffle.nodes;

import com.github.lopoha.coboltruffle.heap.CobolHeap;
import com.github.lopoha.coboltruffle.nodes.expression.CobolFunctionLiteralNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolInvokeNode;
import com.github.lopoha.coboltruffle.runtime.CobolNull;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import java.util.List;

/**
 * Used to call sections inside a file.
 */
@NodeInfo(shortName = "func")
public final class CobolConstructorNode extends CobolFunctionLiteralNode {

  private final CobolHeap cobolHeap;
  private final CobolInvokeNode firstFunction;

  /**
   * Create a function literal node, to call other functions.
   */
  public CobolConstructorNode(String programName,
                              CobolHeap cobolHeap,
                              CobolInvokeNode firstFunction) {
    super(programName);
    assert cobolHeap != null;
    assert firstFunction != null;

    this.cobolHeap = cobolHeap;
    this.firstFunction = firstFunction;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    List<Character> heap = this.cobolHeap.allocate();
    FrameSlot slot
        = frame.getFrameDescriptor().findOrAddFrameSlot(CobolHeap.FRAME_NAME, FrameSlotKind.Object);
    frame.setObject(slot, heap);

    firstFunction.executeVoid(frame);

    // should this be an integer?
    return CobolNull.SINGLETON;
  }
}
