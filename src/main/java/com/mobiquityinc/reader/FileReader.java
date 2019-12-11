package com.mobiquityinc.reader;

import com.mobiquityinc.exception.APIException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * File reader class. Reads a file from a given path and return contents as List of String.
 *
 * @author Diego Fernando Junco
 * @since 2019-12-10
 */
public class FileReader {

  private static final Logger LOG = Logger.getLogger("FileReader");

  /**
   * Reads a file from a given path and return contents as a List of String.
   *
   * @param path the file path.
   * @return file contents as a {@link List} of {@link String}
   */
  public static List<String> readFile(String path) throws APIException {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      LOG.log(Level.INFO, "File read correctly");
      return stream.collect(Collectors.toList());
    } catch (IOException ioe) {
      LOG.log(Level.SEVERE, ioe.getMessage());
      throw new APIException(ioe.getMessage());
    }
  }
}
