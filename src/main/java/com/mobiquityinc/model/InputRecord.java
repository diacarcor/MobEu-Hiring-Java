package com.mobiquityinc.model;

import java.util.List;
import lombok.Data;

/**
 * Class representing an input record.
 *
 * @author Diego Fernando Junco
 * @since 2019-12-10
 */
@Data
public class InputRecord {
  double weight;
  List<Thing> thingsList;
}
