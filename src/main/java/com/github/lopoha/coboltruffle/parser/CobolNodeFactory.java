package com.github.lopoha.coboltruffle.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;

public class CobolNodeFactory {

    private final Map<String, RootCallTarget> allSections = new HashMap<>();
    private String functionName;
    private List<CobolStatementNode> sectionNodes = new ArrayList<>();

    private final CobolLanguage language;

    public CobolNodeFactory(CobolLanguage language) {
        this.language = language;
    }

    public void startSection(String functionName) {
        this.functionName = functionName;
    }

    public void finishSection() {
        FrameDescriptor frameDescriptor = new FrameDescriptor();
        final CobolStatementNode sectionBlock
                = new CobolBlockNode(this.sectionNodes.toArray(new CobolStatementNode[this.sectionNodes.size()]));
        final CobolSectionBodyNode sectionBody = new CobolSectionBodyNode(sectionBlock);
        final CobolRootNode rootNode = new CobolRootNode(language, frameDescriptor, sectionBody, null, this.functionName);
    }

    public void addMove(CobolMoveNode move) {
        this.sectionNodes.add(move);
    }

    public Map<String, RootCallTarget> getAllSections() {
        return this.allSections;
    }
}