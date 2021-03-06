package com.github.lopoha.coboltruffle.nodes.local;

import com.github.lopoha.coboltruffle.CobolException;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.profiles.BranchProfile;

/** Stolen / borrowed from sl. TODO. */
public class CobolReadArgumentNode extends CobolExpressionNode {

  /** The argument number, i.e., the index into the array of arguments. */
  private final int index;

  /**
   * Profiling information, collected by the interpreter, capturing whether the function was called
   * with fewer actual arguments than formal arguments.
   */
  private final BranchProfile outOfBoundsTaken = BranchProfile.create();

  public CobolReadArgumentNode(int index) {
    this.index = index;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    Object[] args = frame.getArguments();
    if (index < args.length) {
      return args[index];
    } else {
      throw new CobolException("Unknown Parameter position", this);
    }
  }
}
