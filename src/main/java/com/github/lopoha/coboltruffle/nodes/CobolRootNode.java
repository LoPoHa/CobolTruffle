package com.github.lopoha.coboltruffle.nodes;

// Modeled after the SLRootNode from SimpleLanguage

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

@NodeInfo(language = "Cobol", description = "The root of all Cobol execution trees")
public class CobolRootNode extends RootNode {
  /** The name of the function, for printing purposes only. */
  private final String name;
  private final SourceSection sourceSection;
  /** The function body that is executed, and specialized during execution. */
  @Child private CobolExpressionNode bodyNode;
  @CompilationFinal private boolean isCloningAllowed;

  /**
   * TODO.
   *
   * @param language todo
   * @param frameDescriptor todo
   * @param bodyNode todo
   * @param sourceSection todo
   * @param name todo
   */
  public CobolRootNode(
      CobolLanguage language,
      FrameDescriptor frameDescriptor,
      CobolExpressionNode bodyNode,
      SourceSection sourceSection,
      String name) {
    super(language, frameDescriptor);
    this.bodyNode = bodyNode;
    this.name = name;
    this.sourceSection = sourceSection;
  }

  @Override
  public SourceSection getSourceSection() {
    return sourceSection;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    assert lookupContextReference(CobolLanguage.class).get() != null;
    return bodyNode.executeGeneric(frame);
  }

  public CobolExpressionNode getBodyNode() {
    return bodyNode;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isCloningAllowed() {
    return isCloningAllowed;
  }

  public void setCloningAllowed(boolean isCloningAllowed) {
    this.isCloningAllowed = isCloningAllowed;
  }

  @Override
  public String toString() {
    return "root " + name;
  }
}
