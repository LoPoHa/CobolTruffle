package com.github.lopoha.coboltruffle.parser;

// Modified version of https://github.com/graalvm/simplelanguage/blob/a25e385dd8626a85e8374f213e708b2f813ab9b7/language/src/main/java/com/oracle/truffle/sl/nodes/controlflow/SLBlockNode.java#L59


import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.BlockNode;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeInfo;
import java.util.Collections;
import java.util.List;

/**
 * A statement node that just executes a list of other statements.
 */
@NodeInfo(shortName = "block", description = "The node implementing a source code block")
public final class CobolBlockNode extends CobolStatementNode
                                  implements BlockNode.ElementExecutor<CobolStatementNode> {

  @Child private BlockNode<CobolStatementNode> block;

  /**
   * TODO.
   * @param bodyNodes TODO
   */
  public CobolBlockNode(CobolStatementNode[] bodyNodes) {
    /*
     * Truffle block nodes cannot be empty, that is why we just set the entire block to null if
     * there are no elements. This is good practice as it safes memory.
     */
    this.block = bodyNodes.length > 0 ? BlockNode.create(bodyNodes, this) : null;
  }

  /**
   * TODO: replace sllanguage comment
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

  @Override
  public void executeVoid(VirtualFrame frame, CobolStatementNode node, int index, int argument) {
    node.executeVoid(frame);
  }

  /**
   * returns all the statements inside this block.
   * @return the statements inside the node.
   */
  public List<CobolStatementNode> getStatements() {
    if (block == null) {
      return Collections.emptyList();
    }
    return List.of(block.getElements());
  }

}