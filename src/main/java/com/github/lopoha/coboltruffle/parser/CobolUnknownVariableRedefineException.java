package com.github.lopoha.coboltruffle.parser;

// todo: position
// todo: base parser exception
public class CobolUnknownVariableRedefineException extends RuntimeException {

  /**
   * Create a new CobolUnknownVariableRedefineException.
   * @param variableName The name of the variable.
   * @param redefineVariable The name of the variable that should be redefined.
   */
  public CobolUnknownVariableRedefineException(String variableName, String redefineVariable) {
    super(
        String.format(
            "The variable %s should be redefined by %s, but %s does not exist.",
            variableName, redefineVariable, variableName));
  }
}