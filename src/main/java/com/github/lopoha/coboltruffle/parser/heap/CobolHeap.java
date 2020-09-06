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

  public char[] allocate() {
    // todo: is it still required to move space to every element?
    return new char[this.heapSize];
  }

  /**
   * Add a HeapBuilder to the heap.
   *
   * @param heapBuilder The HeapBuilder to add to the heap.
   */
  public void addToHeap(HeapBuilder heapBuilder) {
    for (HeapBuilderVariable variable : heapBuilder.getVariables()) {
      if (this.pointerMap.containsKey(variable.name)) {
        // what should happen if it is already defined?
        // nothing? because of the global heap?
        throw new VariableAlreadyDefinedException(variable.name);
      } else {
        final int variableBaseHeapPosition = this.heapSize;
        variable.finalizeVariable();
        this.heapSize += variable.getSize();
        addVariableToPointerMap(variable, variableBaseHeapPosition);
      }
    }
  }

  private CobolHeapPointer getPointerFromBuilder(final HeapBuilderVariable variable,
                                                 final int variableBasePosition) {
    switch (variable.type) {
      case Filler:
        // do nothing
        return null;
      case None: // fallthrough
      case Number: // fallthrough
      case String:
        return new CobolHeapPointerString(
                variable.name,
                variableBasePosition,
                variable.getSize(),
                variable.getDefaultValue(),
                variable.level,
                this.heapName);
      case Const:
        return new CobolHeapPointerConst(
                variable.name,
                variableBasePosition,
                variable.getSize(),
                variable.getDefaultValue(),
                variable.level,
                this.heapName);
      default:
        throw new NotImplementedException();
    }

  }

  private void addChilds(final HeapBuilderVariable variable, int variableHeapPosition) {
    for (HeapBuilderVariable child : variable.getChildren()) {
      if (child.redefines != null) {
        CobolHeapPointer redefinePointer = this.pointerMap.get(child.redefines.name);
        if (redefinePointer == null) {
          throw new CobolUnknownVariableRedefineException(child.redefines.name, child.name);
        }
        addVariableToPointerMap(child, redefinePointer.position);
      } else {
        addVariableToPointerMap(child, variableHeapPosition);
        if (child.type != HeapVariableType.Const) {
          variableHeapPosition += child.getSize();
        }
      }
    }

  }

  private void addVariableToPointerMap(final HeapBuilderVariable variable,
                                       final int variableBasePosition) {

    if (variable.name != null && this.pointerMap.containsKey(variable.name)) {
      throw new VariableAlreadyDefinedException(variable.name);
    }

    CobolHeapPointer pointer = getPointerFromBuilder(variable, variableBasePosition);

    if (pointer != null) {
      this.pointerMap.put(variable.name, pointer);
    }

    addChilds(variable, variableBasePosition);
  }

  /**
   * Get the heap pointer from the given name.
   * Throws a CobolVariableNotFoundException if the name was not found.
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
