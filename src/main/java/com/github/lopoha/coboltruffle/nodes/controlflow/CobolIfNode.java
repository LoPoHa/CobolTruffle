package com.github.lopoha.coboltruffle.nodes.controlflow;

import com.github.lopoha.coboltruffle.CobolException;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.CobolStatementNode;
import com.github.lopoha.coboltruffle.nodes.util.CobolUnboxNodeGen;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;

// from simple language
@NodeInfo(shortName = "if", description = "The node implementing a condional statement")
public final class CobolIfNode extends CobolStatementNode {

  /**
   * Profiling information, collected by the interpreter, capturing the profiling information of the
   * condition. This allows the compiler to generate better code for conditions that are always true
   * or always false. Additionally the {@link CountingConditionProfile} implementation (as opposed
   * to {@link BinaryConditionProfile} implementation) transmits the probability of the condition to
   * be true to the compiler.
   */
  private final ConditionProfile condition = ConditionProfile.createCountingProfile();
  /**
   * The condition of the {@code if}. This in a {@link CobolExpressionNode} because we require a
   * result value. We do not have a node type that can only return a {@code boolean} value, so
   * {@link #evaluateCondition executing the condition} can lead to a type error.
   */
  @Node.Child private CobolExpressionNode conditionNode;
  /**
   * Statement (or {@link CobolBlockNode block}) executed when the condition is true.
   */
  @Node.Child private CobolStatementNode thenPartNode;
  /**
   * Statement (or {@link CobolBlockNode block}) executed when the condition is false.
   */
  @Node.Child private CobolStatementNode elsePartNode;

  /**
   * Create a new if node.
   *
   * @param conditionNode the condition.
   * @param thenPartNode the statement (block) if the condition is true.
   * @param elsePartNode the statement (block) if the condition is false.
   */
  public CobolIfNode(
      CobolExpressionNode conditionNode,
      CobolStatementNode thenPartNode,
      CobolStatementNode elsePartNode) {
    this.conditionNode = CobolUnboxNodeGen.create(conditionNode);
    this.thenPartNode = thenPartNode;
    this.elsePartNode = elsePartNode;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    /*
     * In the interpreter, record profiling information that the condition was executed and with
     * which outcome.
     */
    if (condition.profile(evaluateCondition(frame))) {
      /* Execute the then-branch. */
      thenPartNode.executeVoid(frame);
    } else {
      /* Execute the else-branch (which is optional according to the Cobol syntax). */
      if (elsePartNode != null) {
        elsePartNode.executeVoid(frame);
      }
    }
  }

  private boolean evaluateCondition(VirtualFrame frame) {
    try {
      /*
       * The condition must evaluate to a boolean value, so we call the boolean-specialized
       * execute method.
       */
      return conditionNode.executeBoolean(frame);
    } catch (UnexpectedResultException ex) {
      /*
       * The condition evaluated to a non-boolean result. This is a type error in the Cobol
       * program.
       */
      throw CobolException.typeError(this, ex.getResult());
    }
  }
}
