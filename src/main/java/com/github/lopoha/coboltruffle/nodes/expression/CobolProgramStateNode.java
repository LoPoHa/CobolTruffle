package com.github.lopoha.coboltruffle.nodes.expression;

import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains the complete state of the program.
 * Like an instance of an object.
 */
public class CobolProgramStateNode extends CobolExpressionNode {
  public static final String FRAME_NAME = "LOCALSTATE";

  private final List<Character> localFileHeap;
  private final Map<String, List<Character>> linkageHeap;

  /**
   * Create a new State.
   * Used from the Constructor Node.
   * @param localFileHeap the local file heap
   */
  public CobolProgramStateNode(List<Character> localFileHeap,
                               Map<String, List<Character>> linkageHeap) {
    assert localFileHeap != null;
    assert linkageHeap != null;

    this.localFileHeap = localFileHeap;
    this.linkageHeap = linkageHeap;
  }

  public List<Character> getLocalFileHeap() {
    return this.localFileHeap;
  }

  public List<Character> getLinkageHeap(String key) {
    return this.linkageHeap.get(key);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return this;
  }
}
