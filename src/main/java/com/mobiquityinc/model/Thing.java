package com.mobiquityinc.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing a thing.
 *
 * @author Diego Fernando Junco
 * @since 2019-12-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thing {
  int index;
  double weight;
  String currency;
  BigDecimal cost;
}
