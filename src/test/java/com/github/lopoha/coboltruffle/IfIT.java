package com.github.lopoha.coboltruffle;

import com.github.lopoha.coboltruffle.helper.TestRunner;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Tests for `IF`.
 */
class IfIT {
  @Test
  void equalSameVariable() throws IOException {
    TestRunner.run("if/equalSameVariable");
  }

  @Test
  void equalSameVariableDifferentType() throws IOException {
    TestRunner.run("if/equalSameVariableDifferentType");
  }

  @Test
  void equalDifferentVariable() throws IOException {
    TestRunner.run("if/equalDifferentVariable");
  }
}
