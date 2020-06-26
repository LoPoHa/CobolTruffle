package com.github.lopoha.coboltruffle.parser;

// modeled / copied from SimpleLanguage

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.ReportPolymorphism;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;

@NodeInfo(language = "Cobol", description = "The abstract base node for all Cobol statements")
@GenerateWrapper
@ReportPolymorphism
public abstract class CobolStatementNode extends Node implements InstrumentableNode {

  private static final int NO_SOURCE = -1;
  private static final int UNAVAILABLE_SOURCE = -2;

  private int sourceCharIndex = NO_SOURCE;
  private int sourceLength;

  private boolean hasStatementTag;
  private boolean hasRootTag;

  @Override
  @TruffleBoundary
  public final SourceSection getSourceSection() {
    if (sourceCharIndex == NO_SOURCE) {
      // AST node without source
      return null;
    }
    RootNode rootNode = getRootNode();
    if (rootNode == null) {
      // not yet adopted yet
      return null;
    }
    SourceSection rootSourceSection = rootNode.getSourceSection();
    if (rootSourceSection == null) {
      return null;
    }
    Source source = rootSourceSection.getSource();
    if (sourceCharIndex == UNAVAILABLE_SOURCE) {
      if (hasRootTag && !rootSourceSection.isAvailable()) {
        return rootSourceSection;
      } else {
        return source.createUnavailableSection();
      }
    } else {
      return source.createSection(sourceCharIndex, sourceLength);
    }
  }

  public final boolean hasSource() {
    return sourceCharIndex != NO_SOURCE;
  }

  public final boolean isInstrumentable() {
    return hasSource();
  }

  public final int getSourceCharIndex() {
    return sourceCharIndex;
  }

  public final int getSourceEndIndex() {
    return sourceCharIndex + sourceLength;
  }

  public final int getSourceLength() {
    return sourceLength;
  }

  // invoked by the parser to set the source
  final void setSourceSection(int charIndex, int length) {
    assert sourceCharIndex == NO_SOURCE : "source must only be set once";
    if (charIndex < 0) {
      throw new IllegalArgumentException("charIndex < 0");
    } else if (length < 0) {
      throw new IllegalArgumentException("length < 0");
    }
    this.sourceCharIndex = charIndex;
    this.sourceLength = length;
  }

  public final void setUnavailableSourceSection() {
    assert sourceCharIndex == NO_SOURCE : "source must only be set once";
    this.sourceCharIndex = UNAVAILABLE_SOURCE;
  }

  /**
   * TODO.
   * @param tag todo
   * @return todo
   */
  public boolean hasTag(Class<? extends Tag> tag) {
    if (tag == StandardTags.StatementTag.class) {
      return hasStatementTag;
    } else if (tag == StandardTags.RootTag.class || tag == StandardTags.RootBodyTag.class) {
      return hasRootTag;
    }
    return false;
  }

  public WrapperNode createWrapper(ProbeNode probe) {
    return new CobolStatementNodeWrapper(this, probe);
  }

  /**
   * Execute this node as as statement, where no return value is necessary.
   */
  public abstract void executeVoid(VirtualFrame frame);

  /**
   * Marks this node as being a {@link StandardTags.StatementTag} for instrumentation purposes.
   */
  public final void addStatementTag() {
    hasStatementTag = true;
  }

  /**
   * Marks this node as being a {@link StandardTags.RootTag} and {@link StandardTags.RootBodyTag}
   * for instrumentation purposes.
   */
  public final void addRootTag() {
    hasRootTag = true;
  }

  @Override
  public String toString() {
    return formatSourceSection(this);
  }

  /**
   * Formats a source section of a node in human readable form. If no source section could be
   * found it looks up the parent hierarchy until it finds a source section. Nodes where this was
   * required append a <code>'~'</code> at the end.
   *
   * @param node the node to format.
   * @return a formatted source section string
   */
  public static String formatSourceSection(Node node) {
    if (node == null) {
      return "<unknown>";
    }
    SourceSection section = node.getSourceSection();
    boolean estimated = false;
    if (section == null) {
      section = node.getEncapsulatingSourceSection();
      estimated = true;
    }

    if (section == null || section.getSource() == null) {
      return "<unknown source>";
    } else {
      String sourceName = section.getSource().getName();
      int startLine = section.getStartLine();
      return String.format("%s:%d%s", sourceName, startLine, estimated ? "~" : "");
    }
  }
}
