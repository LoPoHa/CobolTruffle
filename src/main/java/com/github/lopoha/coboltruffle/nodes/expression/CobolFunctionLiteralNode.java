package com.github.lopoha.coboltruffle.nodes.expression;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.parser.CobolNodeFactory;
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

  /**
   * Create a function literal node, to call other functions.
   * @param programName the name of the program, used for prefix.
   * @param functionName the name of the function to be called.
   */
  public CobolFunctionLiteralNode(String programName, String functionName) {
    assert programName != null;
    assert functionName != null;

    functionName = functionName.toLowerCase();

    // todo: replace with a list of buildin functions instead of hard code it here!
    if (functionName.equals("display")) {
      this.functionName = functionName;
    } else {
      this.functionName = CobolNodeFactory.getLocalFunctionName(programName, functionName);
    }
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
