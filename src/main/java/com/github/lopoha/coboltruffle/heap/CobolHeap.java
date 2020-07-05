package com.github.lopoha.coboltruffle.heap;

import com.github.lopoha.coboltruffle.NotImplementedException;
import com.github.lopoha.coboltruffle.parser.CobolUnknownVariableRedefineException;
import com.github.lopoha.coboltruffle.parser.CobolVariableNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CobolHeap {
  public static final String FRAME_NAME = "cobolheap";

  private final HashMap<String, HeapPointer> pointerMap = new HashMap<>();
  private int heapSize = 0;

  public List<Character> allocate() {
    return Collections.nCopies(heapSize, ' ');
  }

  // todo cleanup + better distinguish between heap and heapbuilder...

  // todo bug: a variable can redefine something that was not defined in the same scope. e.g
  // 01 FOO XX.
  // 01 BAR
  //  05 BAZ REDEFINES FOO.
  // ...
  // maybe this should be checked in HeapBuilderVariable when checking the sizes...

  /**
   * Add a HeapBuilder to the heap.
   * @param heapBuilder The HeapBuilder to add to the heap.
   */
  public void addToHeap(HeapBuilder heapBuilder) {
    for (HeapBuilderVariable variable : heapBuilder.getVariables()) {
      if (this.pointerMap.containsKey(variable.variableName)) {
        // what should happen if it is already defined?
        // nothing? because of the global heap?
        throw new VariableAlreadyDefinedException(variable.variableName);
      } else {
        final int variableBaseHeapPosition = this.heapSize;
        variable.finalizeHeapBuilder();
        // todo: respect the default value instead of blank...
        //       this should be done in the addVariableToPointerMap function
        this.heapSize += variable.getSize();
        addVariableToPointerMap(variable, variableBaseHeapPosition, true);
      }
    }
  }

  // todo cleanup!!!
  private void addVariableToPointerMap(final HeapBuilderVariable variable,
                                       final int variableBasePosition,
                                       boolean initialize) {
    // todo should a check if the variable is already defined be here?
    //      this time it should be an error? or not?
    HeapPointer pointer;
    switch (variable.heapVariableType) {
      case Filler: // fallthrough
      case None:   // fallthrough
      case Number: // fallthrough
      case String:
        pointer = new HeapPointerString(variable.variableName,
                                        variableBasePosition,
                                        variable.getSize(),
                                        variable.getValue());
        break;
      case Const:
        pointer = new HeapPointerConst(variable.variableName,
            variableBasePosition,
            variable.getSize(),
            variable.getValue());
        break;
      default:
        throw new NotImplementedException();
    }

    if (this.pointerMap.containsKey(variable.variableName)) {
      throw new VariableAlreadyDefinedException(variable.variableName);
    }
    this.pointerMap.put(variable.variableName, pointer);

    int variableHeapPosition = variableBasePosition;
    for (HeapBuilderVariable child : variable.getChildren()) {
      if (child.redefines != null) {
        HeapPointer redefinePointer = this.pointerMap.get(child.redefines);
        if (redefinePointer == null) {
          throw new CobolUnknownVariableRedefineException(child.redefines, child.variableName);
        }
        addVariableToPointerMap(child, redefinePointer.position, false);
      } else {
        addVariableToPointerMap(child, variableHeapPosition, false);
        variableHeapPosition += child.getSize();
      }
    }

  }

  /**
   * Get the heap pointer from the given name.
   * Throws a CobolVariableNotFoundException if the name was not found.
   * @param variableName the name of the pointer/variable.
   * @return The pointer.
   */
  public HeapPointer getHeapPointer(String variableName) {
    variableName = variableName.toLowerCase();
    if (this.pointerMap.containsKey(variableName)) {
      return this.pointerMap.get(variableName);
    } else {
      throw new CobolVariableNotFoundException(variableName);
    }
  }
}