package com.mobiquityinc.model;

import java.math.BigDecimal;
import lombok.Data;

/**
 * Class representing a thing.
 *
 * @author Diego Fernando Junco
 * @since 2019-12-10
 */
@Data
public class Thing {
  int index;
  float weight;
  BigDecimal cost;
}
