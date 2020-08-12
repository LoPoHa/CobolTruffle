package com.github.lopoha.coboltruffle;

import com.github.lopoha.coboltruffle.helper.TestRunner;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Tests for `CALL`.
 */
public class CallIT {
  @Test
  void simpleCall() throws IOException {
    TestRunner.run("integration/call/simpleCall");
  }
}
