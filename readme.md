# Mobiquity Assignment: Package	Challenge

## Introduction
You want to send your friend a package with different things.  
Each thing you put inside the package has such parameters as index number, weight and cost. The package has a weight limit. Your goal is to determine which things to put into the package so that the total weight is less than or equal to the package limit and the total cost is as large as possible.  
You would prefer to send a package which weighs less in case there is more than one package with the same price.  
## Input sample
Your program should accept as its first argument a path to a filename. The input file contains several lines. Each line is one test case.  
Each line contains the weight that the package can take (before the colon) and the list of things you need to choose. Each thing is enclosed in parentheses where the 1st number is a thing's index number, the 2nd is its weight and the 3rd is its cost. E.g.
```
81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
8 : (1,15.3,€34)
75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)
56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64) 
```

## Output sample
For each set of things that you put into a package provide a new row in the output string (items’ index numbers are separated by comma). E.g.  
```
4
-
2,7
8,9 
```
## Constraints
You should write a class com.mobiquityinc.packer.Packer with a static method named pack. This method accepts the absolute path to a test file as a String. The test file will be in UTF-8 format. The pack method does return the solution as a String. 
Your class should throw an com.mobiquityinc.exception.APIException if incorrect parameters are being passed.  So your signature should look like:
```
public static String pack(String filePath) throws ApiException
```

Additional constraints: 
1. Max weight that a package can take is ≤ 100
2. There might be up to 15 items you need to choose from
3. Max weight and cost of an item is ≤ 10

## Algorithm
The algorithm chosen is a Dynamic Programming for [0/1 knapsack](https://en.wikipedia.org/wiki/Knapsack_problem#0/1_knapsack_problem) problem
This algorithm has several stages:
1. Create a 2-dimensional table with dimensions from 0 to n and 0 to W. Where n quantity of items and W is the package weight limit
2. Use a bottom-up approach to calculate the optimal solution with this table.
3. Find the indexes of each thing to be putted in the package and stores it in a List
4. Check if the solution is optimal in terms of weight, given the following constraint: ```"You would prefer to send a package which weighs less in case there is more than one thing with the same price."```
## Data Structure
* **Thing:** Represents a thing/item that can be added to a package. Thing has the following attributes:
  * Index number
  * Weight
  * Cost
  
* **InputRecord:** Represents a test case. InputRecord has the following attributes:
  * Package weight
  * List of things
  
## Assumptions/Decisions
* TDD practices were used to develop this assignment. See [Red Green Refactor](https://dzone.com/articles/pattern-of-the-month-red-green-refactor)
* Weight is floating point number with up to two decimal places
* 0/1 knapsack dynamic programming algorithm requires integer weights, so it was required to multiply the weight of the package and each item by 100 in order to haver an integer representation of  a two decimal places floating point number.
* If any constraint is not met,an ApiException is thrown and the process no longer continues.
* The [Separation of concerns](https://en.wikipedia.org/wiki/Separation_of_concerns) and [Single responsibility](https://en.wikipedia.org/wiki/Single_responsibility_principle) design principles were applied as follows:
  * FileReader class created to read a file from a given path and return contents as List of String.
  * InputRecordParser class created to parse and validate the contents of the input file to the defined data structure.
  * Solver class responsible for finding a solution for each one of the records in the data structure.

## Getting Started
### Prerequisites
* Java SDK 9
* Maven 3.6.3
### Installing
From the project root folder command line run the following command to build the application
```
mvn clean package
```
### Usage
This application is meant to be used as a maven dependency, NOT as a standalone application.
Add a Maven dependency as follows: 
```
<dependency>
      <groupId>com.mobiquityinc</groupId>
      <artifactId>implementation</artifactId>
      <version>1.0.0</version>
</dependency>
```
Then use the Packer.pack() method. E.g.
```
Path resourceDirectory = Paths.get("src", "test", "resources", "testFile.txt");
String absolutePath = resourceDirectory.toFile().getAbsolutePath();
Packer.pack(absolutePath);
```

## Running the tests

From the project root folder, run the command ```mvn verify``` to execute:
 * Unit tests
 * JaCoCo code coverage test
 * JaCoCo test reports
 * Google Java style check

JaCoCo is setup to have a minimum code coverage ratio of 90%. Anything lower will fail in the build phase as a code quality enforcement policy

After execution JaCoCo test reports can be found in ```/target/site/jacoco/index.html``` 

## Built With

* [Java 9](https://www.oracle.com/java/java9.html) - Language
* [Maven](https://maven.apache.org/) - Dependency Management
* [Lombok](https://projectlombok.org/) - Java library to write less code
* [JaCoCo](https://www.eclemma.org/jacoco/) - Java Code Coverage Library
* [JUnit](https://junit.org) - Testing

## Authors

* **Diego Junco** - [LinkedIn](https://www.linkedin.com/in/diego-junco)