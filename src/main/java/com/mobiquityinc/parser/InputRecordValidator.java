package com.mobiquityinc.parser;

import com.mobiquityinc.exception.ApiException;
import com.mobiquityinc.model.InputRecord;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible to validate an input record.
 *
 * @author Diego Fernando Junco
 * @since 2019-12-18
 */
class InputRecordValidator {

  // regexp to validate line format
  // This regexp matches a string like e.g. "75 : (1,85.31,€29) (2,14.55,€74)"
  private static final String INPUT_RECORD_REGEXP =
      "^\\d+\\s*:\\s*\\(\\d+,\\d*\\.*\\d*,.\\d*\\.*\\d*\\).*$";

  private static final double MAX_PACKAGE_WEIGHT = 100;

  private static final double MAX_ITEM_WEIGHT = 100;

  private static final BigDecimal MAX_ITEM_COST = new BigDecimal(100);

  private static final int MAX_THINGS_PER_LINE = 15;

  private InputRecordValidator() {
  }

  /**
   * Validates if an {@link InputRecord} has a valid values. Otherwise an ApiException is thrown.
   *
   * @param inputRecord an{@link InputRecord} to be validated.
   */
  static void validateInputRecord(InputRecord inputRecord) {
    // Package weight validation
    if (inputRecord.getWeight() > MAX_PACKAGE_WEIGHT) {
      throw new ApiException("Package weight should be less or equal than " + MAX_PACKAGE_WEIGHT);
    }
    // Items per line validation
    if (inputRecord.getThingsList().size() > MAX_THINGS_PER_LINE) {
      throw new ApiException("Items per lines should be less or equal than " + MAX_THINGS_PER_LINE);
    }
    inputRecord
        .getThingsList()
        .forEach(
            thing -> {
              // Thing max weight validation
              if (thing.getWeight() > MAX_ITEM_WEIGHT) {
                throw new ApiException(
                    "Thing weight should be less or equal than " + MAX_ITEM_WEIGHT);
              }
              // Thing max cost validation
              if (thing.getCost().compareTo(MAX_ITEM_COST) > 0) {
                throw new ApiException("Thing cost should be less or equal than " + MAX_ITEM_COST);
              }
            });
  }

  /**
   * Validates if a {@link String} line has a valid format. If not an APIException is thrown.
   *
   * @param line {@link String} representing the lie.
   */
  static void validateLineFormat(String line) {
    Pattern pattern = Pattern.compile(INPUT_RECORD_REGEXP);
    Matcher matcher = pattern.matcher(line);
    if (!matcher.find()) {
      throw new ApiException("Line format incorrect");
    }
  }
}
