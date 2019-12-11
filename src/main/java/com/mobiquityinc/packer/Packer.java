package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;

/**
 * Packer class, Entry point of the library.
 *
 * @author Diego Fernando Junco
 * @since 2019-12-10
 */
public class Packer {

  private Packer() {

  }

  /**
   * Reads a file from a given path and returns the list of things needed to be put into the package
   * so that the total weight is less than or equal to the package limit and the total cost is as
   * large as possible.
   *
   * @param filePath the file path.
   * @return list of things to but put in the packages as a {@link String}
   */
  public static String pack(String filePath) throws APIException {
    return null;
  }
}
