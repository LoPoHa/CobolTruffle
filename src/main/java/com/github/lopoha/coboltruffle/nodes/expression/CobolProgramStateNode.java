package com.github.lopoha.coboltruffle.nodes.expression;

import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.List;

/**
 * Contains the complete state of the program.
 * Like an instance of an object.
 */
public class CobolProgramStateNode extends CobolExpressionNode {
  public static final String FRAME_NAME = "LOCALSTATE";

  private final List<Character> localFileHeap;

  /**
   * Create a new State.
   * Used from the Constructor Node.
   * @param localFileHeap the local file heap
   */
  public CobolProgramStateNode(List<Character> localFileHeap) {
    assert localFileHeap != null;

    this.localFileHeap = localFileHeap;
  }

  public List<Character> getLocalFileHeap() {
    return this.localFileHeap;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return this;
  }
}
