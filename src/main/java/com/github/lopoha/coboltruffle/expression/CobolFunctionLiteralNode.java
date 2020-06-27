package com.github.lopoha.coboltruffle.expression;

import com.github.lopoha.coboltruffle.parser.CobolExpressionNode;
import com.github.lopoha.coboltruffle.parser.CobolLanguage;
import com.github.lopoha.coboltruffle.runtime.CobolSection;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Used for builtin functions with parameters.
 */
@NodeInfo(shortName = "func")
public final class CobolFunctionLiteralNode extends CobolExpressionNode {

  /** The name of the function. */
  private final String functionName;

  @CompilationFinal private CobolSection cachedFunction;

  public CobolFunctionLiteralNode(String functionName) {
    this.functionName = functionName;
  }

  @Override
  public CobolSection executeGeneric(VirtualFrame frame) {
    if (cachedFunction == null) {
      /* We are about to change a @CompilationFinal field. */
      CompilerDirectives.transferToInterpreterAndInvalidate();
      /* First execution of the node: lookup the function in the function registry. */
      cachedFunction
          = lookupContextReference(CobolLanguage.class).get()
                                                       .getFunctionRegistry()
                                                       .lookup(functionName, true);
    }
    return cachedFunction;
  }

}
