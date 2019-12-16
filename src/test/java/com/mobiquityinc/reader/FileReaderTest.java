package com.mobiquityinc.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mobiquityinc.exception.ApiException;
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
class FileReaderTest {

  /**
   * Test wrote to check if the test file was read as a List of String and assert that the list size
   * matches the amount of lines of the file (4) and also check file contents
   */
  @Test
  void readFile_correctly() {
    Path resourceDirectory = Paths.get("src", "test", "resources", "testFile.txt");
    String absolutePath = resourceDirectory.toFile().getAbsolutePath();
    List<String> linesList = FileReader.readFile(absolutePath);
    assertEquals(4, linesList.size());
    assertTrue(linesList.get(0).startsWith("81"));
    assertTrue(linesList.get(3).endsWith("€64)"));
  }

  /** Test wrote to check IF APIException is thrown when the file is no found */
  @Test
  void readFile_notFound() {
    Path resourceDirectory = Paths.get("src", "test", "resources", "testFileNotFound.txt");
    String absolutePath = resourceDirectory.toFile().getAbsolutePath();
    assertThrows(ApiException.class, () -> FileReader.readFile(absolutePath));
  }

  /** Test wrote to check IF APIException is thrown when the file is not UTF-8 encoded */
  @Test
  void readFile_isNotValidUTF8File() {
    Path resourceDirectory = Paths.get("src", "test", "resources", "testFileANSI.txt");
    String absolutePath = resourceDirectory.toFile().getAbsolutePath();
    assertThrows(ApiException.class, () -> FileReader.readFile(absolutePath));
  }
}
