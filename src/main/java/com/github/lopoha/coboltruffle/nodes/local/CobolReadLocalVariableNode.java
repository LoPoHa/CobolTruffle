package com.github.lopoha.coboltruffle.nodes.local;

import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.interop.NodeObjectDescriptor;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameUtil;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.StandardTags.ReadVariableTag;
import com.oracle.truffle.api.instrumentation.Tag;

@NodeField(name = "slot", type = FrameSlot.class)
public abstract class CobolReadLocalVariableNode extends CobolExpressionNode {

  protected abstract FrameSlot getSlot();

  @Specialization
  protected Object readObject(VirtualFrame frame) {
    if (!frame.isObject(getSlot())) {
      CompilerDirectives.transferToInterpreter();
      Object result = frame.getValue(getSlot());
      frame.setObject(getSlot(), result);
      return result;
    }

    return FrameUtil.getObjectSafe(frame, getSlot());
  }

  @Override
  public boolean hasTag(Class<? extends Tag> tag) {
    return tag == ReadVariableTag.class || super.hasTag(tag);
  }

  @Override
  public Object getNodeObject() {
    return NodeObjectDescriptor.readVariable(getSlot().getIdentifier().toString());
  }
}
