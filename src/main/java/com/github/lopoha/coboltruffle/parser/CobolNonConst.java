package com.github.lopoha.coboltruffle.parser;

public class CobolNonConst extends RuntimeException {

  /**
   * Create a new CobolNonConst, to signal that the variable that should be compared
   * is not a constant.
   *
   * @param variableName The name of the variable.
   */
  public CobolNonConst(String variableName) {
    super(String.format("The variable %s was not a const, but is required to be", variableName));
  }
}