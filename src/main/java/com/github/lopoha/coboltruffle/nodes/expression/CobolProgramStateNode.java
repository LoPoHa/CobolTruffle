package com.github.lopoha.coboltruffle.nodes.expression;

import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.expression.heap.RawHeapSlice;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.NodeInfo;
import java.util.Map;

/** Contains the complete state of the program. Like an instance of an object. */
@NodeInfo(shortName = "programstate")
public class CobolProgramStateNode extends CobolExpressionNode implements TruffleObject {
  public static final String FRAME_NAME = "LOCALSTATE";

  private final RawHeapSlice localFileHeap;
  private final Map<String, RawHeapSlice> linkageHeap;

  /**
   * Create a new State. Used from the Constructor Node.
   *
   * @param localFileHeap the local file heap
   * @param linkageHeap the heap that is passed through the linkage section.
   */
  public CobolProgramStateNode(RawHeapSlice localFileHeap,
                               Map<String, RawHeapSlice> linkageHeap) {
    assert localFileHeap != null;
    assert linkageHeap != null;

    this.localFileHeap = localFileHeap;
    this.linkageHeap = linkageHeap;
  }

  public char[] getLocalFileHeap() {
    return this.localFileHeap.getHeapSlice();
  }

  public void setHeapValue(String heapName, int heapPosition, char[] value) {
    assert heapPosition > 0;
    assert value != null;

    if (heapName == null) {
      localFileHeap.setHeapValue(heapPosition, value);
    } else {
      getLinkageHeap(heapName).setHeapValue(heapPosition, value);
    }
  }


  public RawHeapSlice getLinkageHeap(String key) {
    return this.linkageHeap.get(key);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return this;
  }
}
