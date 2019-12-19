package com.mobiquityinc.parser;

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

  private InputRecordParser() {}

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
  public static List<InputRecord> parseFile(List<String> file) {
    List<InputRecord> inputRecordList = new ArrayList<>();

    file.forEach(
        record -> {
          InputRecordValidator.validateLineFormat(record);
          String[] recordArray = record.split(":");
          InputRecord inputRecord = new InputRecord();
          inputRecord.setWeight(Float.parseFloat(recordArray[0]));
          List<Thing> thingList = new ArrayList<>();
          Pattern pattern = Pattern.compile(THING_REGEXP);
          Matcher matcher = pattern.matcher(recordArray[1]);
          while (matcher.find()) {
            thingList.add(
                new Thing(
                    Integer.parseInt(matcher.group("index")),
                    Double.parseDouble(matcher.group("weight")),
                    matcher.group("currency"),
                    new BigDecimal(matcher.group("cost"))));
          }
          inputRecord.setThingsList(thingList);
          InputRecordValidator.validateInputRecord(inputRecord);
          inputRecordList.add(inputRecord);
        });
    return inputRecordList;
  }
}