package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.parser.antlr.*;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
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

@NodeInfo(language = "CobolTruffle", description = "The abstract base node for all SL statements")
@GenerateWrapper
public abstract class BaseStatementNode extends Node implements InstrumentableNode {
    private boolean hasStatementTag;
    private boolean hasRootTag;

    @Override
    @TruffleBoundary
    public final SourceSection getSourceSection() {
        return null;
    }

    public abstract void executeVoid(VirtualFrame frame);

    public WrapperNode createWrapper(ProbeNode probe) {
        return new BaseStatementNodeWrapper(this, probe);
    }

    public final boolean isInstrumentable() {
        // todo: check what this should do
        return false;
    }

    @Override
    public String toString() {
        // todo
        return "TODO";
    }
}