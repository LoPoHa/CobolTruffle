package com.github.lopoha.coboltruffle.builtins;

import com.github.lopoha.coboltruffle.CobolException;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

/**
 * Base class for all builtin functions. It contains the Truffle DSL annotation {@link NodeChild}
 * that defines the function arguments.
 */
@NodeChild(value = "arguments", type = CobolExpressionNode[].class)
@GenerateNodeFactory
public abstract class CobolBuiltinNode extends CobolExpressionNode {


  @Override
  public final Object executeGeneric(VirtualFrame frame) {
    try {
      return execute(frame);
    } catch (UnsupportedSpecializationException e) {
      throw CobolException.typeError(e.getNode(), e.getSuppliedValues());
    }
  }

  @Override
  public final boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
    return super.executeBoolean(frame);
  }

  @Override
  public final long executeLong(VirtualFrame frame) throws UnexpectedResultException {
    return super.executeLong(frame);
  }

  @Override
  public final void executeVoid(VirtualFrame frame) {
    super.executeVoid(frame);
  }

  protected abstract Object execute(VirtualFrame frame);
}
