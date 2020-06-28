package com.github.lopoha.coboltruffle.heap;

import java.util.ArrayList;
import java.util.List;

// todo: better name (as always...)
// todo: split heapbuilder into heapbuilder and variable class
// todo: handle redefines
// todo: handle arrays (table)
public class HeapBuilder {
  private final List<HeapBuilderVariable> variables = new ArrayList<>();

  // root level is either 1 or 77 (todo: what does the 77 mean?)
  private boolean isRootLevel(int level) {
    return level == 1 || level == 77;
  }

  /**
   * Add a heapVariable to the heap.
   * @param heapVariable The variable to add.
   */
  // todo handle levels
  public void add(HeapBuilderVariable heapVariable) {
    if (isRootLevel(heapVariable.level)) {
      this.variables.add(heapVariable);
    } else {
      // better handle not root values for error messages
      this.variables.get(this.variables.size() - 1).add(heapVariable);
    }
  }


  /**
   * Pretty print the Heap.
   */
  public void prettyPrint() {
    for (HeapBuilderVariable heapVariable : this.variables) {
      heapVariable.prettyPrint(0);
    }
  }

  public List<HeapBuilderVariable> getVariables() {
    return new ArrayList<>(this.variables);
  }
}