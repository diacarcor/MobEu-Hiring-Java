package com.mobiquityinc.packer;

import com.mobiquityinc.model.InputRecord;
import com.mobiquityinc.model.Thing;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class responsible for find a solution for each records in the List.
 *
 * @author Diego Fernando Junco
 * @since 2019-12-11
 */
class Solver {

  private static final int HUNDRED_MULTIPLIER = 100;

  private Solver() {
  }

  /**
   * Finds a solution for every record.
   *
   * @param records as a {@link List} of {@link InputRecord}
   * @return {@link String} with the solution
   */
  static String findSolution(List<InputRecord> records) {
    List<String> results = new ArrayList<>();
    records.forEach(
        record -> {
          ArrayList<Integer> weightList = new ArrayList<>();
          ArrayList<Integer> valueList = new ArrayList<>();
          ArrayList<Integer> indexList = new ArrayList<>();
          List<Thing> things = record.getThingsList();
          things.forEach(
              thing -> {
                valueList.add(thing.getCost().intValue());
                // Weight needed to be multiplied by 100 to treat it as an integer.
                // The knapsack 0-1 dynamic programming algorithm needs weight an value as integers
                weightList.add((int) (thing.getWeight() * HUNDRED_MULTIPLIER));
                indexList.add(thing.getIndex());
              });
          int packageWeight = (int) (record.getWeight() * HUNDRED_MULTIPLIER);
          Integer[] weightArray = weightList.toArray(new Integer[0]);
          Integer[] costArray = valueList.toArray(new Integer[0]);
          Integer[] indexArray = indexList.toArray(new Integer[0]);
          results.add(findSolutionPerRecord(packageWeight, weightArray, costArray, indexArray));
        });

    return results.stream()
        .map(String::valueOf)
        .collect(Collectors.joining(System.lineSeparator()));
  }

  /**
   * Find a solution for a single record.
   *
   * @param packageWeight {@link int} containing the package max weight
   * @param weightArray an array of {@link Integer} containing the items weight
   * @param costArray an array of {@link Integer} containing the items cost
   * @param indexArray an array of {@link Integer} containing the items indexes
   * @return {@link String} with the solution for a single record
   */
  private static String findSolutionPerRecord(
      int packageWeight, Integer[] weightArray, Integer[] costArray, Integer[] indexArray) {

    int[][] knapSackTable = populateKnapsackTable(packageWeight, weightArray, costArray);

    // Find indexes of each thing to be putted in the package and stores it in a List
    List<Integer> resultsList =
        findSolutionIndexes(packageWeight, indexArray, costArray, weightArray, knapSackTable);

    // If there's no solution return a "-"
    if (resultsList.isEmpty()) {
      return "-";
    }

    // Checks if the solution is optimal in terms of weight given the constraint:
    // "You would prefer to send a package which weighs less in case there is more than one thing
    // with the same price."
    List<Integer> resultsListFinal =
        findOptimalSolution(resultsList, indexArray, costArray, weightArray);

    Collections.sort(resultsListFinal);
    return resultsListFinal.stream().map(String::valueOf).collect(Collectors.joining(","));
  }

  /**
   * Populates knapsack dynamic programming table.
   *
   * @param packageWeight {@link int} containing the package max weight
   * @param weightArray an array of {@link Integer} containing the items weight
   * @param costArray an array of {@link Integer} containing the items cost
   * @return an bidimensional int array with the knapsack table
   */
  private static int[][] populateKnapsackTable(
      int packageWeight, Integer[] weightArray, Integer[] costArray) {
    int i;
    int w;
    int[][] arrayK = new int[weightArray.length + 1][packageWeight + 1];

    // Build table arrayK[][] in bottom up manner
    for (i = 0; i <= weightArray.length; i++) {
      for (w = 0; w <= packageWeight; w++) {
        if (i == 0 || w == 0) {
          arrayK[i][w] = 0;
        } else if (weightArray[i - 1] <= w) {
          arrayK[i][w] =
              Math.max(costArray[i - 1] + arrayK[i - 1][w - weightArray[i - 1]], arrayK[i - 1][w]);
        } else {
          arrayK[i][w] = arrayK[i - 1][w];
        }
      }
    }
    return arrayK;
  }

  /**
   * Find the solution indexes given a knapsack table.
   *
   * @param packageWeight int containing the package max weight
   * @param indexArray an array of {@link Integer} containing the items indexes
   * @param costArray an array of {@link Integer} containing the items cost
   * @param weightArray an array of {@link Integer} containing the items weight
   * @param knapSackTable a bidimensional int array representing the knapsack table
   * @return a {@link List} of {@link Integer} containing the solution indexes
   */
  private static List<Integer> findSolutionIndexes(
      int packageWeight,
      Integer[] indexArray,
      Integer[] costArray,
      Integer[] weightArray,
      int[][] knapSackTable) {
    List<Integer> resultsList = new ArrayList<>();
    int solutionTotalCost = knapSackTable[costArray.length][packageWeight];
    for (int i = indexArray.length; i > 0 && solutionTotalCost > 0; i--) {
      // either the result comes from the top
      // (K[i-1][w]) or from (val[i-1] + K[i-1]
      // [w-wt[i-1]]) as in Knapsack table. If
      // it comes from the latter one/ it means
      // the item is included.
      if (solutionTotalCost != knapSackTable[i - 1][packageWeight]) {
        // This item is included.
        resultsList.add(indexArray[i - 1]);
        // Since this weight is included its
        // value is deducted
        solutionTotalCost = solutionTotalCost - costArray[i - 1];
        packageWeight = packageWeight - weightArray[i - 1];
      }
    }
    return resultsList;
  }

  /**
   * Find an optimal solution in terms of weight given the constraint: "You would prefer to send a
   * package which weighs less in case there is more than one thing with the same price."
   *
   * @param resultsList {@link List} of {@link Integer} containing the solution
   * @param indexArray an array of {@link Integer} containing the items indexes
   * @param costArray an array of {@link Integer} containing the items cost
   * @param weightArray an array of {@link Integer} containing the items weight
   * @return a {@link List} of {@link Integer} with the optimal solution
   */
  private static List<Integer> findOptimalSolution(
      List<Integer> resultsList, Integer[] indexArray, Integer[] costArray, Integer[] weightArray) {
    List<Integer> resultsListFinal = new ArrayList<>(resultsList);
    resultsList.forEach(
        record -> {
          int recordIndex = Arrays.asList(indexArray).indexOf(record);
          int value = costArray[recordIndex];
          List<Integer> matchingIndexes = findIndexes(costArray, value);
          matchingIndexes.forEach(
              matchingIndex -> {
                if (!resultsList.contains(indexArray[matchingIndex])
                    && weightArray[matchingIndex] < weightArray[recordIndex]) {
                  resultsListFinal.remove(record);
                  resultsListFinal.add(indexArray[matchingIndex]);
                }
              });
        });
    return resultsListFinal;
  }

  /**
   * Search all matching indexes from a given array an a value.
   *
   * @param array {@link Integer} array to search from
   * @param value Integer to be searched for in the array
   * @return a {@link List} {@link Integer} with all matching indexes
   */
  private static List<Integer> findIndexes(Integer[] array, int value) {
    List<Integer> matchingIndexes = new ArrayList<>();
    for (int t = 0; t < array.length; t++) {
      int element = array[t];
      if (value == element) {
        matchingIndexes.add(t);
      }
    }
    return matchingIndexes;
  }
}
