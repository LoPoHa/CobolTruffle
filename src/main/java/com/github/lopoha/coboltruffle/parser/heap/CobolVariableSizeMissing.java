package com.github.lopoha.coboltruffle.parser.heap;

// todo: position
// todo: base parser exception
class CobolVariableSizeMissing extends RuntimeException {
  public CobolVariableSizeMissing(String variableName) {
    super(
        String.format(
            "The variable %s doesn't define its size and child size is also 0.",
            variableName));
  }
}
