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
  //    TestRunner.run("move/workingStorageNumber");
  //  }

  @Test
  void workingStorageString() throws IOException {
    TestRunner.run("move/workingStorageString");
  }

  @Test
  void workingStorageStringSubLevel() throws IOException {
    TestRunner.run("move/workingStorageStringSubLevel");
  }

  @Test
  void workingStorageVariable() throws IOException {
    TestRunner.run("move/workingStorageVariable");
  }

  @Test
  void workingStorageSoace() throws IOException {
    TestRunner.run("move/workingStorageSpace");
  }
}
