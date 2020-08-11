package com.github.lopoha.coboltruffle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.lopoha.coboltruffle.helper.ProgramRun;
import com.github.lopoha.coboltruffle.helper.RunResult;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Tests for `IF`.
 */
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
  void equalSameVariableDifferentType() throws IOException {
    String file = "if/equalSameVariableDifferentType.cbl";
    ProgramRun programResult = ProgramRun.runProgram(file);
    assertEquals(RunResult.Success, programResult.runResult);
    assertEquals(String.format("TRUE%nTRUE%n"), programResult.sysout);
    assertEquals("", programResult.errout);
  }

  @Test
  void equalDifferentVariable() throws IOException {
    String file = "if/equalDifferentVariable.cbl";
    ProgramRun programResult = ProgramRun.runProgram(file);
    assertEquals(RunResult.Success, programResult.runResult);
    assertEquals(String.format("FALSE%nFALSE%nFALSE%nFALSE%nFALSE%nFALSE%n"),
                 programResult.sysout);
    assertEquals("", programResult.errout);
  }
}
