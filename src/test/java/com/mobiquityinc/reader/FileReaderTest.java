package com.mobiquityinc.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderTest {

  /**
   * Test wrote to check if the test file was read as a list of String and list size matches the
   * amount of lines of the file (4) and check file contents
   */
  @Test
  public void readFile() {
    Path resourceDirectory = Paths.get("src", "test", "resources", "testFile.txt");
    String absolutePath = resourceDirectory.toFile().getAbsolutePath();
    List<String> linesList = FileReader.readFile(absolutePath);
    assertEquals(linesList.size(), 4);
    assertTrue(linesList.get(0).startsWith("81"));
    assertTrue(linesList.get(3).endsWith("€64)"));
  }
}
