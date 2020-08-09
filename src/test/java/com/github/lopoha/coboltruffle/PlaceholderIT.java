package com.github.lopoha.coboltruffle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.lopoha.coboltruffle.helper.Helper;
import com.github.lopoha.coboltruffle.helper.ProgramRun;
import com.github.lopoha.coboltruffle.helper.RunResult;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class PlaceholderIT {

  @Test
  void helloWorld() throws IOException {
    //simple hello world
    String file = "helloworld.cbl";
    ProgramRun programResult = Helper.runProgram(file);
    assertEquals(programResult.runResult, RunResult.Success);
    assertEquals(String.format("HELLO WORLD%n"), programResult.sysout);
    assertEquals("", programResult.errout);
  }
}
