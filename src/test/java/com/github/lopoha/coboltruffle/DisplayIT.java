package com.github.lopoha.coboltruffle;

import com.github.lopoha.coboltruffle.helper.TestRunner;
import org.junit.jupiter.api.Test;

/**
 * Tests for `DISPLAY`.
 * <br />
 * Since all/most of the other tests depend on the display output of the program(s),
 * it is really important that display works 100% correct!
 */
public class DisplayIT {
  @Test
  void helloWorld() {
    TestRunner.run("integration/display/helloworld");
  }

  @Test
  void variables() {
    TestRunner.run("integration/display/variables");
  }

  @Test
  void variablesCopy() {
    TestRunner.run("integration/display/variablesCopy");
  }

  // doesn't work currently
  //  @Test
  //  void number() throws IOException {
  //    TestRunner.run("unit/display/number");
  //  }
}
