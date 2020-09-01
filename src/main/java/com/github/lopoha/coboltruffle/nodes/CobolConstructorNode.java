package com.github.lopoha.coboltruffle.nodes;

import com.github.lopoha.coboltruffle.nodes.expression.CobolFunctionLiteralNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolProgramStateNode;
import com.github.lopoha.coboltruffle.nodes.expression.heap.RawHeapSlice;
import com.github.lopoha.coboltruffle.parser.heap.CobolHeap;
import com.github.lopoha.coboltruffle.runtime.CobolSection;
import com.github.lopoha.coboltruffle.runtime.CobolSectionRegistry;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Used to call sections inside a file. */
@NodeInfo(shortName = "func")
public final class CobolConstructorNode extends CobolFunctionLiteralNode {

  private final CobolHeap cobolHeap;
  private final List<String> inputParameterNames;
  private final String firstFunctionName;
  private final CobolSectionRegistry fileLocalFunctions;

  @CompilerDirectives.CompilationFinal private CobolSection cachedFunction;

  /**
   * Create a cobol constructer, that creates the heap, initializes the variables and calls the
   * first frunction.
   * @param programName Name of the program.
   * @param cobolHeap The heap that is created.
   * @param inputParameterNames names of the input parameters, that gets passed to the program.
   * @param firstFunctionName name of the first function to call.
   * @param fileLocalFunctions all the local functions (sections) inside the program.
   */
  public CobolConstructorNode(
      String programName,
      CobolHeap cobolHeap,
      List<String> inputParameterNames,
      String firstFunctionName,
      CobolSectionRegistry fileLocalFunctions) {
    super(programName);
    assert cobolHeap != null;
    assert inputParameterNames != null;
    assert firstFunctionName != null;
    assert fileLocalFunctions != null;

    this.cobolHeap = cobolHeap;
    this.inputParameterNames = inputParameterNames;
    this.firstFunctionName = firstFunctionName;
    this.fileLocalFunctions = fileLocalFunctions;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    final Map<String, RawHeapSlice> inputParameters = new HashMap<>();
    final Object[] frameArguments = frame.getArguments();
    for (int i = 0; i < inputParameterNames.size(); i++) {
      // todo: make it safe
      RawHeapSlice value = (RawHeapSlice) frameArguments[i];
      inputParameters.put(this.inputParameterNames.get(i), value);
    }

    List<Character> heap = this.cobolHeap.allocate();
    CobolProgramStateNode programStateNode = new CobolProgramStateNode(heap, inputParameters);
    FrameSlot slot =
        frame
            .getFrameDescriptor()
            .findOrAddFrameSlot(CobolProgramStateNode.FRAME_NAME, FrameSlotKind.Object);
    frame.setObject(slot, programStateNode);

    this.cobolHeap
        .getRootPointers()
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
