package com.github.lopoha.coboltruffle.helper;

public class ProgramRun {
  public final RunResult runResult;
  public final String sysout;
  public final String errout;

  ProgramRun(RunResult runResult, String sysout, String errout) {
    this.runResult = runResult;
    this.sysout = sysout;
    this.errout = errout;
  }
}
