package com.github.lopoha.coboltruffle.parser.heap;

import com.github.lopoha.coboltruffle.parser.CobolVariableNotFoundException;
import java.util.ArrayList;
import java.util.List;

// todo: better name (as always...)
// todo: split heapbuilder into heapbuilder and variable class
// todo: handle redefines
// todo: handle arrays (table)
public class HeapBuilderOld {
  private final List<HeapBuilderVariable> variables = new ArrayList<>();
  private HeapBuilderVariable lastNotConstVariable;

  /**
   * Find a variable by name.
   *
   * @param name The name of the variable.
   * @return The variable if found, else an exception is thrown.
   */
  public HeapBuilderVariable findVariable(String name) {
    for (HeapBuilderVariable variable : variables) {
      HeapBuilderVariable found = variable.findVariable(name);
      if (found != null) {
        return found;
      }
    }
    throw new CobolVariableNotFoundException(name);
  }

  // root level is either 1 or 77 (todo: what does the 77 mean?)
  private boolean isRootLevel(int level) {
    return level == 1 || level == 77;
  }

  /**
   * Add a heapVariable to the heap.
   *
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
    if (heapVariable.type != HeapVariableType.Const) {
      this.lastNotConstVariable = heapVariable;
    }
  }

  /**
   * Add all the variables of a heap builder to this.
   *
   * @param heapBuilder the heap builder to add.
   */
  public void add(HeapBuilderOld heapBuilder) {
    for (HeapBuilderVariable variable : heapBuilder.variables) {
      this.add(variable);
    }
  }

  /**
   * Get the last heap builder variable. Used for level 88 to get the parent.
   *
   * @return last variable.
   */
  public HeapBuilderVariable getLastVariable() {
    return this.lastNotConstVariable;
  }

  public List<HeapBuilderVariable> getVariables() {
    return new ArrayList<>(this.variables);
  }
}
