package com.github.lopoha.coboltruffle.parser;

class CobolCallParameterMismatchException extends RuntimeException {
  CobolCallParameterMismatchException(String programName,
      String callParamName,
      String calledProgramName,
      String calledParamName) {
    super(String.format("The program %s called %s with parameter %s, but %s was expected.",
        programName,
        calledProgramName,
        callParamName,
        calledParamName));
  }

}
