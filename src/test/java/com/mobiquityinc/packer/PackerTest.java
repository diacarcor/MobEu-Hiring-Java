package com.mobiquityinc.packer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Packer test class.
 *
 * @author Diego Fernando Junco
 * @since 2019-12-12
 */
class PackerTest {

  private static String absolutePath;
  /** Setup test data. */
  @BeforeAll
  static void setup() {
    Path resourceDirectory = Paths.get("src", "test", "resources", "testFile.txt");
    absolutePath = resourceDirectory.toFile().getAbsolutePath();
  }

  /** Checks if a solution for a given file path is correct */
  @Test
  void pack_correctly() {
    assertEquals("4"+System.lineSeparator()+"-"+System.lineSeparator()+"2,7"+System.lineSeparator()+"8,9", Packer.pack(absolutePath));
  }
}
