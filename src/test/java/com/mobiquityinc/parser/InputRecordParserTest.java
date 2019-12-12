package com.mobiquityinc.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.InputRecord;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Input record parser test class.
 *
 * @author Diego Fernando Junco
 * @since 2019-12-10
 */
public class InputRecordParserTest {

  /**
   * Checks if a test input is able to be parsed as a {@link List} of {@link InputRecord} and assert
   * that the record contents are ok.
   */
  @Test
  public void parseFile_correctly() throws APIException {
    List<String> linesList = new ArrayList<>();
    linesList.add(
        "75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)");
    List<InputRecord> inputRecordList = InputRecordParser.parseFile(linesList);
    assertEquals(inputRecordList.size(), 1);
    assertEquals(inputRecordList.get(0).getWeight(), 75);
    assertEquals(inputRecordList.get(0).getThingsList().size(), 9);
    assertEquals(inputRecordList.get(0).getThingsList().get(0).getIndex(), 1);
    assertEquals(inputRecordList.get(0).getThingsList().get(0).getWeight(), 85.31);
    assertEquals(inputRecordList.get(0).getThingsList().get(0).getCurrency(), "€");
    assertEquals(inputRecordList.get(0).getThingsList().get(0).getCost(), new BigDecimal(29));
    assertEquals(inputRecordList.get(0).getThingsList().get(8).getIndex(), 9);
    assertEquals(inputRecordList.get(0).getThingsList().get(8).getWeight(), 89.95);
    assertEquals(inputRecordList.get(0).getThingsList().get(8).getCurrency(), "€");
    assertEquals(inputRecordList.get(0).getThingsList().get(8).getCost(), new BigDecimal(78));
  }

  /** Checks if a incorrect formatted test input throws an APIException. */
  @Test
  public void parseFile_incorrectFormat() {
    List<String> linesList = new ArrayList<>();
    linesList.add(
        "75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)");
    linesList.add("35 : (190.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16)");
    assertThrows(APIException.class, () -> InputRecordParser.parseFile(linesList));
  }
}
