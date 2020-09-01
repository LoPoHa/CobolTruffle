package com.github.lopoha.coboltruffle.parser;

class CobolVariableDefaultValueTooBig extends RuntimeException {
  CobolVariableDefaultValueTooBig(String variableName, int size, String value) {
    super(String.format(
        "The variable \"%s\" with the given length [%d] "
            + "was too small for \"%s\" (length: [%d]",
        variableName, size, value, value.length()));
  }

}
