package com.github.lopoha.coboltruffle.helper;

public class ExpectedResult {
  private RunResult runResult;
  private String[] sysout;
  private String[] syserr;

  RunResult getRunResult() {
    return this.runResult;
  }

  String getSysout() {
    return getString(sysout);
  }

  String getSyserr() {
    return getString(syserr);
  }

  private String getString(String[] input) {
    if (input.length > 0) {
      String output = String.join(String.format("%n"), input);
      return output + String.format("%n");
    } else {
      return "";
    }
  }
}
