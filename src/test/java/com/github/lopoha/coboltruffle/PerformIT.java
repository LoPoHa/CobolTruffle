package com.github.lopoha.coboltruffle;

import com.github.lopoha.coboltruffle.helper.TestRunner;
import org.junit.jupiter.api.Test;

public class PerformIT {
  @Test
  void simpleSection() {
    TestRunner.run("integration/perform/simpleSection");
  }
}
