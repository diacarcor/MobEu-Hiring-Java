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
    assertEquals(1, inputRecordList.size());
    assertEquals(75, inputRecordList.get(0).getWeight());
    assertEquals(9, inputRecordList.get(0).getThingsList().size());
    assertEquals(1, inputRecordList.get(0).getThingsList().get(0).getIndex());
    assertEquals(85.31, inputRecordList.get(0).getThingsList().get(0).getWeight());
    assertEquals("€", inputRecordList.get(0).getThingsList().get(0).getCurrency());
    assertEquals(new BigDecimal(29), inputRecordList.get(0).getThingsList().get(0).getCost());
    assertEquals(9, inputRecordList.get(0).getThingsList().get(8).getIndex());
    assertEquals(89.95, inputRecordList.get(0).getThingsList().get(8).getWeight());
    assertEquals("€", inputRecordList.get(0).getThingsList().get(8).getCurrency());
    assertEquals(new BigDecimal(78), inputRecordList.get(0).getThingsList().get(8).getCost());
  }

  /** Checks if an incorrect formatted input test throws an APIException. */
  @Test
  public void parseFile_incorrectFormat() {
    List<String> linesList = new ArrayList<>();
    linesList.add(
        "75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)");
    linesList.add("35 : (190.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16)");
    assertThrows(APIException.class, () -> InputRecordParser.parseFile(linesList));
  }

  /** Checks if a line surpassing 100 weight throws an APIException. */
  @Test
  public void parseFile_packageMaxWeightSurpassed() {
    List<String> linesList = new ArrayList<>();
    linesList.add(
        "75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)");
    linesList.add("201 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16)");
    assertThrows(APIException.class, () -> InputRecordParser.parseFile(linesList));
  }

  /** Checks if a line surpassing 15 items throws an APIException. */
  @Test
  public void parseFile_lineMaxItemsSurpassed() {
    List<String> linesList = new ArrayList<>();
    linesList.add(
        "75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)");
    linesList.add(
        "20 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,14.55,€74) (6,90.72,€13) (7,33.80,€40) (8,43.15,€10) (9,37.97,€16) (10,14.55,€74) (11,90.72,€13) (12,33.80,€40) (13,43.15,€10) (14,37.97,€16) (15,14.55,€74) (16,43.15,€10)");
    assertThrows(APIException.class, () -> InputRecordParser.parseFile(linesList));
  }

  /** Checks if an item surpassing 100 weight throws an APIException. */
  @Test
  public void parseFile_itemMaxWeightSurpassed() {
    List<String> linesList = new ArrayList<>();
    linesList.add(
        "75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)");
    linesList.add(
        "20 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,14.55,€74) (6,102.72,€13) (7,33.80,€40) (8,43.15,€10) (9,37.97,€16) (10,14.55,€74) (11,90.72,€13) (12,33.80,€40) (13,43.15,€10) (14,37.97,€16) (15,14.55,€74)");
    assertThrows(APIException.class, () -> InputRecordParser.parseFile(linesList));
  }
}
