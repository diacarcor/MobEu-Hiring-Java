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
                weightList.add((int) (thing.getWeight() * 100));
                indexList.add(thing.getIndex());
              });
          int packageWeight = (int) (record.getWeight() * 100);
          int n = record.getThingsList().size();
          Integer[] wt = weightList.toArray(new Integer[0]);
          Integer[] val = valueList.toArray(new Integer[0]);
          Integer[] index = indexList.toArray(new Integer[0]);
          results.add(findSolutionPerRecord(packageWeight, wt, val, n, index));
        });

    return results.stream()
        .map(String::valueOf)
        .collect(Collectors.joining(System.lineSeparator()));
  }

  /**
   * Find a solution for a single record.
   *
   * @param packageWeight {@link int} containing the package max weight
   * @param wt an array of {@link Integer} containing the items weight
   * @param val an array of {@link Integer} containing the items cost
   * @param n {@link int} defining the number of items
   * @param index an array of {@link Integer} containing the items indexes
   * @return {@link String} with the solution for a single record
   */
  private static String findSolutionPerRecord(
      int packageWeight, Integer[] wt, Integer[] val, int n, Integer[] index) {
    List<Integer> resultsList = new ArrayList<>();
    List<Integer> resultsIndexList = new ArrayList<>();

    int i;
    int w;
    int[][] arrayK = new int[n + 1][packageWeight + 1];

    // Build table arrayK[][] in bottom up manner
    for (i = 0; i <= n; i++) {
      for (w = 0; w <= packageWeight; w++) {
        if (i == 0 || w == 0) {
          arrayK[i][w] = 0;
        } else if (wt[i - 1] <= w) {
          arrayK[i][w] = Math.max(val[i - 1] + arrayK[i - 1][w - wt[i - 1]], arrayK[i - 1][w]);
        } else {
          arrayK[i][w] = arrayK[i - 1][w];
        }
      }
    }

    // stores the result(max value) of Knapsack
    int res = arrayK[n][packageWeight];

    // Find indexes of each thing to be putted in the package and stores it in a List
    w = packageWeight;
    for (i = n; i > 0 && res > 0; i--) {
      // either the result comes from the top
      // (K[i-1][w]) or from (val[i-1] + K[i-1]
      // [w-wt[i-1]]) as in Knapsack table. If
      // it comes from the latter one/ it means
      // the item is included.
      if (res != arrayK[i - 1][w]) {
        // This item is included.
        resultsList.add(index[i - 1]);
        resultsIndexList.add(i - 1);
        // Since this weight is included its
        // value is deducted
        res = res - val[i - 1];
        w = w - wt[i - 1];
      }
    }

    // If there's no solution return a "-"
    if (resultsList.isEmpty()) {
      return "-";
    }

    // Checks if the solution is optimal in terms of weight given the constraint:
    // "You would prefer to send a package which weighs less in case there is more than one thing
    // with the same price."
    List<Integer> resultsListFinal = new ArrayList<>(resultsList);
    resultsList.forEach(
        record -> {
          int recordIndex = Arrays.asList(index).indexOf(record);
          int value = val[recordIndex];
          List<Integer> matchingIndexes = findIndexes(val, value);
          matchingIndexes.forEach(
              matchingIndex -> {
                if (!resultsIndexList.contains(matchingIndex)
                    && wt[matchingIndex] < wt[recordIndex]) {
                  resultsListFinal.remove(record);
                  resultsListFinal.add(index[matchingIndex]);
                }
              });
        });

    Collections.sort(resultsListFinal);
    return resultsListFinal.stream().map(String::valueOf).collect(Collectors.joining(","));
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