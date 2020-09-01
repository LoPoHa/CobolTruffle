package com.github.lopoha.coboltruffle.nodes.expression;

import com.github.lopoha.coboltruffle.runtime.CobolSection;
import com.github.lopoha.coboltruffle.runtime.CobolSectionRegistry;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

/** Used to call sections inside a file. */
@NodeInfo(shortName = "func")
public final class CobolLocalFunctionLiteralNode extends CobolFunctionLiteralNode {

  private final CobolSectionRegistry fileLocalFunctions;

  @CompilationFinal private CobolSection cachedFunction;

  /**
   * Create a function literal node, to call other functions.
   *
   * @param functionName the name of the function to be called.
   * @param fileLocalFunctions the CobolSectionRegistry that contains all the function inside the
   *     file.
   */
  public CobolLocalFunctionLiteralNode(
      String functionName, CobolSectionRegistry fileLocalFunctions) {
    super(functionName);
    assert fileLocalFunctions != null;

    this.fileLocalFunctions = fileLocalFunctions;
  }

  @Override
  public CobolSection executeGeneric(VirtualFrame frame) {
    if (cachedFunction == null) {
      /* We are about to change a @CompilationFinal field. */
      CompilerDirectives.transferToInterpreterAndInvalidate();
      /* First execution of the node: lookup the function in the function registry. */
      cachedFunction = this.fileLocalFunctions.lookup(functionName, true);
    }
    return cachedFunction;
  }
}
