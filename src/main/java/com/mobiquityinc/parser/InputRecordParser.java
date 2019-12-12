package com.mobiquityinc.parser;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.InputRecord;
import com.mobiquityinc.model.Thing;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible for parse the contents of the input file.
 *
 * @author Diego Fernando Junco
 * @since 2019-12-10
 */
public class InputRecordParser {

  // regexp to validate line format
  // This regexp matches a string like e.g. "75 : (1,85.31,€29) (2,14.55,€74)"
  private static final String INPUT_RECORD_REGEXP =
      "^\\d+\\s*:\\s*\\(\\d+,\\d*\\.*\\d*,.\\d*\\.*\\d*\\).*$";

  // regexp for thing format with named groups: index, weight, currency and cost
  // This regexp matches a string like e.g. "(6,76.25,€75)"
  private static final String THING_REGEXP =
      "\\((?<index>\\d+),(?<weight>\\d*\\.*\\d*),(?<currency>.)(?<cost>\\d*\\.*\\d*)\\)";

  /**
   * Parse a file from a {@link List} of {@link String} representing the file.
   *
   * @param file {@link List} of {@link String} representing the file.
   * @return parsed file as a {@link List} of {@link InputRecord}
   */
  public static List<InputRecord> parseFile(List<String> file) throws APIException {
    List<InputRecord> inputRecordList = new ArrayList<>();

    file.forEach(
        (record) -> {
          validateLineFormat(record);
          String[] recordArray = record.split(":");
          InputRecord inputRecord = new InputRecord();
          inputRecord.setWeight(Float.parseFloat(recordArray[0]));
          List<Thing> thingList = new ArrayList<>();
          Pattern pattern = Pattern.compile(THING_REGEXP);
          Matcher matcher = pattern.matcher(recordArray[1]);
          while (matcher.find()) {
            Thing thing = new Thing();
            thing.setIndex(Integer.parseInt(matcher.group("index")));
            thing.setWeight(Double.parseDouble(matcher.group("weight")));
            thing.setCurrency(matcher.group("currency"));
            thing.setCost(new BigDecimal(matcher.group("cost")));
            thingList.add(thing);
          }
          inputRecord.setThingsList(thingList);
          inputRecordList.add(inputRecord);
        });
    return inputRecordList;
  }

  /**
   * Validates if a {@link String} line has a valid format. If not an APIException is thrown.
   *
   * @param line {@link String} representing the lie.
   */
  private static void validateLineFormat(String line) throws APIException {
    Pattern pattern = Pattern.compile(INPUT_RECORD_REGEXP);
    Matcher matcher = pattern.matcher(line);
    if (!matcher.find()) {
      throw new APIException("Line format incorrect");
    }
  }
}
