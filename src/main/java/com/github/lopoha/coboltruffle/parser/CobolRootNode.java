package com.github.lopoha.coboltruffle.parser;

// Modeled after the SLRootNode from SimpleLanguage

import com.github.lopoha.coboltruffle.parser.antlr.*;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

@NodeInfo(language = "Cobol", description = "The root of all Cobol execution trees")
public class CobolRootNode extends RootNode {
    /** The function body that is executed, and specialized during execution. */
    @Child private CobolExpressionNode bodyNode;

    /** The name of the function, for printing purposes only. */
    private final String name;

    @CompilationFinal private boolean isCloningAllowed;

    private final SourceSection sourceSection;

    public CobolRootNode(CobolLanguage language, FrameDescriptor frameDescriptor, CobolExpressionNode bodyNode, SourceSection sourceSection, String name) {
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

    public void setCloningAllowed(boolean isCloningAllowed) {
        this.isCloningAllowed = isCloningAllowed;
    }

    @Override
    public boolean isCloningAllowed() {
        return isCloningAllowed;
    }

    @Override
    public String toString() {
        return "root " + name;
    }
}
