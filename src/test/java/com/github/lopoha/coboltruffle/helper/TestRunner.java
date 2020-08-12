package com.github.lopoha.coboltruffle.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestRunner {
  private static final Gson gson = new Gson();

  /**
   * Run a test cobol program and compares the result to the expected one from the json file.
   * @param fileName file path/name with the resources folder as root and without file ending.
   *                 a corresponding .json file is expected to be there too.
   * @throws IOException if a file is not found.
   */
  public static void run(String fileName) throws IOException {
    String cobolFileName = "./src/test/resources/" + fileName + ".cbl";
    String jsonFileName  = "./src/test/resources/" + fileName + ".json";
    String content = Files.readString(Path.of(jsonFileName));
    ExpectedResult expectedResult = gson.fromJson(content, ExpectedResult.class);
    ProgramRun programRun = ProgramRun.runProgram(cobolFileName);

    assertEquals(expectedResult.getRunResult(), programRun.runResult);
    assertEquals(expectedResult.getSysout(), programRun.sysout);
    assertEquals(expectedResult.getSyserr(), programRun.syserr);
  }
}
