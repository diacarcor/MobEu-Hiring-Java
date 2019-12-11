package com.mobiquityinc.parser;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.InputRecord;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for parse the contents of the input file.
 *
 * @author Diego Fernando Junco
 * @since 2019-12-10
 */
public class InputRecordParser {

  private static final String INPUT_RECORD_REGEXP =
      "^\\d+\\s*:\\s*\\(\\d+,\\d*\\.*\\d*,.\\d*\\.*\\d*\\).*$";
  private static final String THING_REGEXP = "\\(\\d+,\\d*\\.*\\d*,.\\d*\\.*\\d*\\s*\\)";

  /**
   * Parse a file from a {@link List} of {@link String} representing the file.
   *
   * @param file {@link List} of {@link String} representing the file.
   * @return parsed file as a {@link List} of {@link InputRecord}
   */
  public static List<InputRecord> parseFile(List<String> file) throws APIException {
    return new ArrayList<>();
  }
}
