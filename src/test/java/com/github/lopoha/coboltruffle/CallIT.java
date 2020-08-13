package com.github.lopoha.coboltruffle;

import com.github.lopoha.coboltruffle.helper.TestRunner;
import org.junit.jupiter.api.Test;

/**
 * Tests for `CALL`.
 */
public class CallIT {
  @Test
  void simpleCall() {
    TestRunner.run("integration/call/simpleCall");
  }

  @Test
  void multipleCallSameProgram() {
    TestRunner.run("integration/call/multipleCallSameProgram");
  }
}
