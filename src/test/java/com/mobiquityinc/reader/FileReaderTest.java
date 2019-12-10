package com.mobiquityinc.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderTest {

  @Test
  public void readFile() {
    List<String> linesList = FileReader.readFile("test/resources/testFile.txt");
    assertEquals(linesList.size(), 4);
  }
}
