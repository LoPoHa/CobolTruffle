package com.github.lopoha.coboltruffle.parser.heap;

import com.github.lopoha.coboltruffle.NotImplementedException;
import com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointer;
import com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointerConst;
import com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointerString;
import com.github.lopoha.coboltruffle.parser.CobolUnknownVariableRedefineException;
import com.github.lopoha.coboltruffle.parser.CobolVariableNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CobolHeap {
  private final HashMap<String, CobolHeapPointer> pointerMap = new HashMap<>();
  private final String heapName;
  private int heapSize = 0;

  public CobolHeap() {
    heapName = null;
  }

  public CobolHeap(String heapName) {
    this.heapName = heapName;
  }

  public List<Character> allocate() {
    return new ArrayList<>(Collections.nCopies(heapSize, ' '));
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
   *
   * @param heapBuilder The HeapBuilder to add to the heap.
   */
  public void addToHeap(HeapBuilderOld heapBuilder) {
    for (HeapBuilderVariableOld variable : heapBuilder.getVariables()) {
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
        addVariableToPointerMap(variable, variableBaseHeapPosition);
      }
    }
  }

  // todo cleanup!!!
  private void addVariableToPointerMap(
      final HeapBuilderVariableOld variable, final int variableBasePosition) {
    // todo should a check if the variable is already defined be here?
    //      this time it should be an error? or not?
    CobolHeapPointer pointer = null;
    switch (variable.heapVariableType) {
      case Filler:
        // do nothing
        break;
      case None: // fallthrough
      case Number: // fallthrough
      case String:
        pointer =
            new CobolHeapPointerString(
                variable.variableName,
                variableBasePosition,
                variable.getSize(),
                variable.getValue(),
                variable.level,
                this.heapName);
        break;
      case Const:
        pointer =
            new CobolHeapPointerConst(
                variable.variableName,
                variableBasePosition,
                variable.getSize(),
                variable.getValue(),
                variable.level,
                this.heapName);
        break;
      default:
        throw new NotImplementedException();
    }

    if (variable.variableName != null && this.pointerMap.containsKey(variable.variableName)) {
      throw new VariableAlreadyDefinedException(variable.variableName);
    }

    if (pointer != null) {
      this.pointerMap.put(variable.variableName, pointer);
    }

    int variableHeapPosition = variableBasePosition;
    for (HeapBuilderVariableOld child : variable.getChildren()) {
      if (child.redefines != null) {
        CobolHeapPointer redefinePointer = this.pointerMap.get(child.redefines);
        if (redefinePointer == null) {
          throw new CobolUnknownVariableRedefineException(child.redefines, child.variableName);
        }
        addVariableToPointerMap(child, redefinePointer.position);
      } else {
        addVariableToPointerMap(child, variableHeapPosition);
        variableHeapPosition += child.getSize();
      }
    }
  }

  /**
   * Get the heap pointer from the given name. Throws a CobolVariableNotFoundException if the name
   * was not found.
   *
   * @param variableName the name of the pointer/variable.
   * @return The pointer.
   */
  public CobolHeapPointer getHeapPointer(String variableName) {
    variableName = variableName.toLowerCase();
    if (this.pointerMap.containsKey(variableName)) {
      return this.pointerMap.get(variableName);
    } else {
      throw new CobolVariableNotFoundException(variableName);
    }
  }

  public boolean containsHeapPointer(String variableName) {
    return this.pointerMap.containsKey(variableName.toLowerCase());
  }

  /**
   * Collect all the root (level 1) pointer and return it as a list.
   *
   * @return the list with all the root heap pointers.
   */
  public List<CobolHeapPointer> getRootPointers() {
    return this.pointerMap.values().stream().filter(x -> x.level == 1).collect(Collectors.toList());
  }
}
