package com.github.lopoha.coboltruffle;

import com.github.lopoha.coboltruffle.helper.ProgramRun;
import com.github.lopoha.coboltruffle.helper.RunResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IfIT {
  @Test
  void equalsstring() throws IOException {
    //simple hello world
    String file = "if/equalsstring.cbl";
    ProgramRun programResult = ProgramRun.runProgram(file);
    assertEquals(RunResult.Success, programResult.runResult);
    assertEquals(String.format("HELLO WORLD%n"), programResult.sysout);
    assertEquals("", programResult.errout);
  }
}
