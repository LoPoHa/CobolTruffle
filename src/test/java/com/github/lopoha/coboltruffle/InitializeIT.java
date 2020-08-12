package com.github.lopoha.coboltruffle;

import com.github.lopoha.coboltruffle.helper.TestRunner;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Tests for `INITIALIZE` and the implicit initialization at the program start.
 */
class InitializeIT {
  // todo: how should program start initialization work, if a sub level has a initialization too?

  @Test
  void programStartSimple() throws IOException {
    TestRunner.run("integration/initialize/programStartSimple");
  }
}
