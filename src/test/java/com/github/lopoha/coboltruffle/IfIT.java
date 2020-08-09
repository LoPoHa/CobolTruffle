package com.github.lopoha.coboltruffle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.lopoha.coboltruffle.helper.ProgramRun;
import com.github.lopoha.coboltruffle.helper.RunResult;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class IfIT {
  @Test
  void equalsSameVariable() throws IOException {
    //simple hello world
    String file = "if/equalsSameVariable.cbl";
    ProgramRun programResult = ProgramRun.runProgram(file);
    assertEquals(RunResult.Success, programResult.runResult);
    assertEquals(String.format("TRUE%n"), programResult.sysout);
    assertEquals("", programResult.errout);
  }
}
