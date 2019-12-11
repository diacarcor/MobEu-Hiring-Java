package com.mobiquityinc.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mobiquityinc.exception.APIException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * File reader test class.
 *
 * @author Diego Fernando Junco
 * @since 2019-12-10
 */
public class FileReaderTest {

  /**
   * Test wrote to check if the test file was read as a List of String and assert that the list size
   * matches the amount of lines of the file (4) and also check file contents
   */
  @Test
  public void readFile() throws APIException {
    Path resourceDirectory = Paths.get("src", "test", "resources", "testFile.txt");
    String absolutePath = resourceDirectory.toFile().getAbsolutePath();
    List<String> linesList = FileReader.readFile(absolutePath);
    assertEquals(linesList.size(), 4);
    assertTrue(linesList.get(0).startsWith("81"));
    assertTrue(linesList.get(3).endsWith("€64)"));
  }
}
