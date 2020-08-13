package com.github.lopoha.coboltruffle;

import com.github.lopoha.coboltruffle.helper.TestRunner;
import java.io.IOException;
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
}
