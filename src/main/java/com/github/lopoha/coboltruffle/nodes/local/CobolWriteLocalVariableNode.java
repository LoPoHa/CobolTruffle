package com.github.lopoha.coboltruffle.nodes.local;

import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.interop.NodeObjectDescriptor;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.StandardTags.WriteVariableTag;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;

/**
 * Store a variable on the current frame.
 */
@NodeChild("valueNode")
@NodeField(name = "slot", type = FrameSlot.class)
@NodeField(name = "nameNode", type = CobolExpressionNode.class)
public abstract class CobolWriteLocalVariableNode extends CobolExpressionNode {
  protected abstract FrameSlot getSlot();

  protected abstract CobolExpressionNode getNameNode();

  @Specialization(guards = "isLongOrIllegal(frame)")
  protected long writeLong(VirtualFrame frame, long value) {
    frame.getFrameDescriptor().setFrameSlotKind(getSlot(), FrameSlotKind.Long);

    frame.setLong(getSlot(), value);
    return value;
  }

  @Specialization(guards = "isBooleanOrIllegal(frame)")
  protected boolean writeBoolean(VirtualFrame frame, boolean value) {
    frame.getFrameDescriptor().setFrameSlotKind(getSlot(), FrameSlotKind.Boolean);

    frame.setBoolean(getSlot(), value);
    return value;
  }

  @Specialization(replaces = {"writeLong", "writeBoolean"})
  protected Object write(VirtualFrame frame, Object value) {
    frame.getFrameDescriptor().setFrameSlotKind(getSlot(), FrameSlotKind.Object);

    frame.setObject(getSlot(), value);
    return value;
  }

  protected boolean isLongOrIllegal(VirtualFrame frame) {
    final FrameSlotKind kind = frame.getFrameDescriptor().getFrameSlotKind(getSlot());
    return kind == FrameSlotKind.Long || kind == FrameSlotKind.Illegal;
  }

  protected boolean isBooleanOrIllegal(VirtualFrame frame) {
    final FrameSlotKind kind = frame.getFrameDescriptor().getFrameSlotKind(getSlot());
    return kind == FrameSlotKind.Boolean || kind == FrameSlotKind.Illegal;
  }

  @Override
  public boolean hasTag(Class<? extends Tag> tag) {
    return tag == WriteVariableTag.class || super.hasTag(tag);
  }

  @Override
  public Object getNodeObject() {
    CobolExpressionNode nameNode = getNameNode();
    SourceSection nameSourceSection;
    if (nameNode.getSourceCharIndex() == -1) {
      nameSourceSection = null;
    } else {
      SourceSection rootSourceSection = getRootNode().getSourceSection();
      if (rootSourceSection == null) {
        nameSourceSection = null;
      } else {
        Source source = rootSourceSection.getSource();
        nameSourceSection
            = source.createSection(nameNode.getSourceCharIndex(), nameNode.getSourceLength());
      }
    }
    return NodeObjectDescriptor.writeVariable(getSlot().getIdentifier().toString(),
                                              nameSourceSection);
  }
}
