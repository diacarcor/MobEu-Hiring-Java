package com.mobiquityinc.reader;

import com.mobiquityinc.exception.ApiException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * File reader class. Reads a file from a given path and return contents as List of String.
 *
 * @author Diego Fernando Junco
 * @since 2019-12-10
 */
public class FileReader {

  private FileReader() {
  }

  /**
   * Reads a file from a given path and return contents as a List of String.
   *
   * @param path the file path.
   * @return file contents as a {@link List} of {@link String}
   */
  public static List<String> readFile(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
      return stream.collect(Collectors.toList());
    } catch (UncheckedIOException ioe) {
      throw new ApiException(ioe.getCause().getMessage(), ioe.getCause());
    } catch (IOException ioe) {
      throw new ApiException(ioe.getMessage(), ioe);
    }
  }
}
