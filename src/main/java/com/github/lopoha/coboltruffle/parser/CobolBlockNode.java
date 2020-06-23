package com.github.lopoha.coboltruffle.parser;

// Modified version of https://github.com/graalvm/simplelanguage/blob/a25e385dd8626a85e8374f213e708b2f813ab9b7/language/src/main/java/com/oracle/truffle/sl/nodes/controlflow/SLBlockNode.java#L59


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.BlockNode;
import com.oracle.truffle.api.nodes.BlockNode.ElementExecutor;
import com.oracle.truffle.api.nodes.ControlFlowException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * A statement node that just executes a list of other statements.
 */
@NodeInfo(shortName = "block", description = "The node implementing a source code block")
public final class CobolBlockNode extends CobolStatementNode implements BlockNode.ElementExecutor<CobolStatementNode> {

    /**
     * The block of child nodes. Using the block node allows Truffle to split the block into
     * multiple groups for compilation if the method is too big. This is an optional API.
     * Alternatively, you may just use your own block node, with a
     * {@link com.oracle.truffle.api.nodes.Node.Children @Children} field. However, this prevents
     * Truffle from compiling big methods, so these methods might fail to compile with a compilation
     * bailout.
     */
    @Child private BlockNode<CobolStatementNode> block;

    public CobolBlockNode(CobolStatementNode[] bodyNodes) {
        /*
         * Truffle block nodes cannot be empty, that is why we just set the entire block to null if
         * there are no elements. This is good practice as it safes memory.
         */
        this.block = bodyNodes.length > 0 ? BlockNode.create(bodyNodes, this) : null;
    }

    /**
     * Execute all block statements. The block node makes sure that {@link ExplodeLoop full
     * unrolling} of the loop is triggered during compilation. This allows the
     * {@link BaseStatementNode#executeVoid} method of all children to be inlined.
     */
    @Override
    public void executeVoid(VirtualFrame frame) {
        if (this.block != null) {
            this.block.executeVoid(frame, BlockNode.NO_ARGUMENT);
        }
    }

    public List<CobolStatementNode> getStatements() {
        if (block == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(Arrays.asList(block.getElements()));
    }

    @Override
    public void executeVoid(VirtualFrame frame, CobolStatementNode node, int index, int argument) {
        node.executeVoid(frame);
    }
}