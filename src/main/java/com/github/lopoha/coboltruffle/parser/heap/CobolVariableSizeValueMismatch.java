package com.github.lopoha.coboltruffle.parser.heap;

// todo: position
// todo: base parser exception
class CobolVariableSizeValueMismatch extends RuntimeException {
  public CobolVariableSizeValueMismatch(String variableName, int expectedSize, int valueSize) {
    super(
        String.format(
            "The variable %s s defined with size %d but it's default value is %d long.",
            variableName,
            expectedSize,
            valueSize));
  }
}
