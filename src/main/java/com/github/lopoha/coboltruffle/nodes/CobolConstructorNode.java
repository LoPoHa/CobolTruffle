package com.github.lopoha.coboltruffle.nodes;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.heap.CobolHeap;
import com.github.lopoha.coboltruffle.nodes.expression.CobolFunctionLiteralNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolInvokeNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolProgramStateNode;
import com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointer;
import com.github.lopoha.coboltruffle.nodes.local.CobolWriteLocalVariableNodeGen;
import com.github.lopoha.coboltruffle.runtime.CobolNull;
import com.github.lopoha.coboltruffle.runtime.CobolSection;
import com.github.lopoha.coboltruffle.runtime.CobolSectionRegistry;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import java.util.List;

/**
 * Used to call sections inside a file.
 */
@NodeInfo(shortName = "func")
public final class CobolConstructorNode extends CobolFunctionLiteralNode {

  private final CobolHeap cobolHeap;
  private final String firstFunctionName;
  private final CobolSectionRegistry fileLocalFunctions;

  @CompilerDirectives.CompilationFinal
  private CobolSection cachedFunction;

  /**
   * Create a function literal node, to call other functions.
   */
  public CobolConstructorNode(String programName,
                              CobolHeap cobolHeap,
                              String firstFunctionName,
                              CobolSectionRegistry fileLocalFunctions) {
    super(programName);
    assert cobolHeap != null;
    assert firstFunctionName != null;
    assert fileLocalFunctions != null;

    this.cobolHeap = cobolHeap;
    this.firstFunctionName = firstFunctionName;
    this.fileLocalFunctions = fileLocalFunctions;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    List<Character> heap = this.cobolHeap.allocate();
    CobolProgramStateNode programStateNode = new CobolProgramStateNode(heap);
    FrameSlot slot = frame.getFrameDescriptor()
                          .findOrAddFrameSlot(CobolProgramStateNode.FRAME_NAME,
                                              FrameSlotKind.Object);
    frame.setObject(slot, programStateNode);


    this.cobolHeap.getRootPointers()
                  .forEach(pointer -> new CobolInitializeNode(pointer).executeVoid(frame));


    if (cachedFunction == null) {
      /* We are about to change a @CompilationFinal field. */
      CompilerDirectives.transferToInterpreterAndInvalidate();
      /* First execution of the node: lookup the function in the function registry. */
      cachedFunction = this.fileLocalFunctions.lookup(firstFunctionName, true);
    }

    return cachedFunction;
  }
}
