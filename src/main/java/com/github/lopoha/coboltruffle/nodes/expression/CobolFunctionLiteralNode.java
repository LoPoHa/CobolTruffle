package com.github.lopoha.coboltruffle.nodes.expression;

import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.runtime.CobolSection;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Base class for all function calls.
 */
@NodeInfo(shortName = "func")
public abstract class CobolFunctionLiteralNode extends CobolExpressionNode {

  /** The name of the function. */
  protected final String functionName;

  @CompilationFinal private CobolSection cachedFunction;

  /**
   * Create a function literal node, to call other functions.
   * @param functionName the name of the function to be called.
   */
  public CobolFunctionLiteralNode(String functionName) {
    assert functionName != null;

    this.functionName = functionName.toLowerCase();
  }
}
