package com.mobiquityinc.packer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mobiquityinc.model.InputRecord;
import com.mobiquityinc.model.Thing;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Solver test class.
 *
 * @author Diego Fernando Junco
 * @since 2019-12-12
 */
class SolverTest {

  private static List<InputRecord> records;

  /** Setup test data. */
  @BeforeAll
  static void setup() {
    records = new ArrayList<>();
    InputRecord record = new InputRecord();
    List<Thing> things = new ArrayList<>();
    Thing thing1 = new Thing(1, 85.31, "€", new BigDecimal(29));
    Thing thing2 = new Thing(2, 14.55, "€", new BigDecimal(74));
    Thing thing3 = new Thing(3, 3.98, "€", new BigDecimal(16));
    Thing thing4 = new Thing(4, 26.24, "€", new BigDecimal(55));
    Thing thing5 = new Thing(5, 63.69, "€", new BigDecimal(52));
    Thing thing6 = new Thing(6, 76.25, "€", new BigDecimal(75));
    Thing thing7 = new Thing(7, 60.02, "€", new BigDecimal(74));
    Thing thing8 = new Thing(8, 93.18, "€", new BigDecimal(35));
    Thing thing9 = new Thing(9, 89.95, "€", new BigDecimal(78));
    things.add(thing1);
    things.add(thing2);
    things.add(thing3);
    things.add(thing4);
    things.add(thing5);
    things.add(thing6);
    things.add(thing7);
    things.add(thing8);
    things.add(thing9);
    record.setWeight(75);
    record.setThingsList(things);
    records.add(record);
  }

  /** Checks if a solution for a given {@link List} of {@link InputRecord} is correct */
  @Test
  void findSolution_correctly() {
    assertEquals("2,7", Solver.findSolution(records));
  }
}
