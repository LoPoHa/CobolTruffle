package com.github.lopoha.coboltruffle.parser;

class CobolCallProgramParameterCountMismatchException extends RuntimeException {
  CobolCallProgramParameterCountMismatchException(String programName,
      int callParamSize,
      String calledProgramName,
      int expectedParamSize) {
    super(String.format("The program %s called %s with %d parameter, but %s expected %d",
        programName,
        calledProgramName,
        callParamSize,
        calledProgramName,
        expectedParamSize));
  }
}
