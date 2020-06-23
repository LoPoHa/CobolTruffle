package com.github.lopoha.coboltruffle.parser;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "move", description = "set a the value of a variable")
public class CobolMoveNode extends CobolStatementNode {
    private final String value;
    private final HeapPointer target;

    public CobolMoveNode(String value, HeapPointer target) {
        this.value = value;
        this.target = target;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        this.target.setValue(this.value);
    }
}
