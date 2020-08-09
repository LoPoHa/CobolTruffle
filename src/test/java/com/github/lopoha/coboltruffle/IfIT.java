package com.github.lopoha.coboltruffle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.lopoha.coboltruffle.helper.ProgramRun;
import com.github.lopoha.coboltruffle.helper.RunResult;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class IfIT {
  @Test
  void equalSameVariable() throws IOException {
    String file = "if/equalSameVariable.cbl";
    ProgramRun programResult = ProgramRun.runProgram(file);
    assertEquals(RunResult.Success, programResult.runResult);
    assertEquals(String.format("TRUE%nTRUE%n"), programResult.sysout);
    assertEquals("", programResult.errout);
  }

  @Test
  void equalNotSameVariable() throws IOException {
    String file = "if/equalNotSameVariable.cbl";
    ProgramRun programResult = ProgramRun.runProgram(file);
    assertEquals(RunResult.Success, programResult.runResult);
    assertEquals(String.format("FALSE%nFALSE%n"), programResult.sysout);
    assertEquals("", programResult.errout);
  }
}
