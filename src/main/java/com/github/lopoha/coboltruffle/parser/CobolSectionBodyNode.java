package com.github.lopoha.coboltruffle.parser;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "section_body")
public class CobolSectionBodyNode extends CobolExpressionNode {
    @Child
    private CobolStatementNode bodyNode;

    public CobolSectionBodyNode(CobolStatementNode bodyNode) {
        this.bodyNode = bodyNode;
        addRootTag();
    }


    @Override
    public Object executeGeneric(VirtualFrame frame) {
        bodyNode.executeVoid(frame);
        return CobolNull.SINGLETON;
    }
}
