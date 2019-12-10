package com.mobiquityinc.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {

  private static final Logger LOG = Logger.getLogger("FileReader");

  /**
   * Reads a file from a given path and return contents as List of String
   *
   * @param path the file path.
   * @return file contents as List<String>
   */
  public static List<String> readFile(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      LOG.log(Level.INFO, "File read correctly");
      return stream.collect(Collectors.toList());
    } catch (IOException ioe) {
      LOG.log(Level.SEVERE, ioe.getMessage());
    }
    return new ArrayList<>();
  }
}
