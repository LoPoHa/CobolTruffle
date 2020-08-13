package com.github.lopoha.coboltruffle;

import com.github.lopoha.coboltruffle.helper.TestRunner;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Tests for `MOVE`.
 */
class MoveIT {
  //  currently not implemented for numbers
  //    this should test the alignment too!
  //
  //  @Test
  //  void workingStorageNumber() throws IOException {
  //    TestRunner.run("integration/move/workingStorageNumber");
  //  }

  @Test
  void workingStorageString() {
    TestRunner.run("integration/move/workingStorageString");
  }

  @Test
  void workingStorageStringSubLevel() {
    TestRunner.run("integration/move/workingStorageStringSubLevel");
  }

  @Test
  void workingStorageVariable() {
    TestRunner.run("integration/move/workingStorageVariable");
  }

  @Test
  void workingStorageSpace() {
    TestRunner.run("integration/move/workingStorageSpace");
  }
}
