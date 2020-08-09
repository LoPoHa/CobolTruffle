package com.github.lopoha.coboltruffle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.lopoha.coboltruffle.helper.ProgramRun;
import com.github.lopoha.coboltruffle.helper.RunResult;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class DisplayIT {

  @Test
  void helloWorld() throws IOException {
    //simple hello world
    String file = "display/helloworld.cbl";
    ProgramRun programResult = ProgramRun.runProgram(file);
    assertEquals(RunResult.Success, programResult.runResult);
    assertEquals(String.format("HELLO WORLD%n"), programResult.sysout);
    assertEquals("", programResult.errout);
  }

  @Test
  void variables() throws IOException {
    //simple hello world
    String file = "display/variables.cbl";
    ProgramRun programResult = ProgramRun.runProgram(file);
    assertEquals(RunResult.Success, programResult.runResult);
    assertEquals(String.format("TEST%n1234%n"), programResult.sysout);
    assertEquals("", programResult.errout);
  }
}
