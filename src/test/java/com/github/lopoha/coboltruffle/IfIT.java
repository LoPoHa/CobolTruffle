package com.github.lopoha.coboltruffle;

import com.github.lopoha.coboltruffle.helper.TestRunner;
import org.junit.jupiter.api.Test;

/**
 * Tests for `IF`.
 */
class IfIT {
  @Test
  void equalSameVariable() {
    TestRunner.run("integration/if/equalSameVariable");
  }

  @Test
  void equalSameVariableDifferentType() {
    TestRunner.run("integration/if/equalSameVariableDifferentType");
  }

  @Test
  void equalDifferentVariable() {
    TestRunner.run("integration/if/equalDifferentVariable");
  }

  @Test
  void constCompare() {
    TestRunner.run("integration/if/const");
  }

  @Test
  void isNumeric() {
    TestRunner.run("integration/if/isNumeric");
  }

  @Test
  void not() {
    TestRunner.run("integration/if/not");
  }

  @Test
  void and() {
    TestRunner.run("integration/if/and");
  }

  @Test
  void or() {
    TestRunner.run("integration/if/or");
  }

  @Test
  void andOr() {
    TestRunner.run("integration/if/andor");
  }

  @Test
  void parenthesis() {
    TestRunner.run("integration/if/parenthesis");
  }
}
