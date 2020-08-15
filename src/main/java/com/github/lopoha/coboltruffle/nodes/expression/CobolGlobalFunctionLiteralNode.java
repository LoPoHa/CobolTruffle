package com.github.lopoha.coboltruffle.nodes.expression;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.runtime.CobolSection;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

/** Used for builtin functions + programs. */
@NodeInfo(shortName = "func")
public final class CobolGlobalFunctionLiteralNode extends CobolFunctionLiteralNode {
  @CompilationFinal private CobolSection cachedFunction;

  /**
   * Create a function literal node, to call other functions.
   *
   * @param functionName the name of the function to be called.
   */
  public CobolGlobalFunctionLiteralNode(String functionName) {
    super(functionName);
  }

  @Override
  public CobolSection executeGeneric(VirtualFrame frame) {
    if (cachedFunction == null) {
      /* We are about to change a @CompilationFinal field. */
      CompilerDirectives.transferToInterpreterAndInvalidate();
      /* First execution of the node: lookup the function in the function registry. */
      cachedFunction =
          lookupContextReference(CobolLanguage.class)
              .get()
              .getFunctionRegistry()
              .lookup(functionName, true);
    }
    return cachedFunction;
  }
}
