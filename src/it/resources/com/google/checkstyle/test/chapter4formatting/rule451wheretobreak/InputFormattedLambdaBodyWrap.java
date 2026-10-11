package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/** Some javdoc. */
public class InputFormattedLambdaBodyWrap {

  private List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

  private BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;

  private Supplier<String> getMessage = () -> "Hello, world!";

  private void printNames() {
    names.forEach(name -> System.out.println(name));
  }

  private void runTask() {
    Runnable runTask =
        () -> {
          System.out.println("Starting task");
          System.out.println("Task completed");
        };
  }

  private void executeNestedLambda() {
    Function<Function<Integer, Integer>, Integer> applyFunc = f -> f.apply(5);
    System.out.println(applyFunc.apply(x -> x * x));
  }

  private void sumOfSquares() {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
    int sum = numbers.stream().filter(n -> n % 2 == 1).mapToInt(n -> n * n).sum();
  }

  private void showComplexNestedLambda() {
    // terminology: () - parentheses, [] - brackets, {} - braces
    // Code below is definitely "unbraced expression" and it is single (but not a single-line),
    // fact that is parentheses-ed is still definition of single.
    // Multiple expressions will imply curly braces and `;`.
    // so case below is ok
    Function<Integer, Function<Integer, Integer>> createAdder = x -> (y -> x + y);
  }

  private void foo() {
    BiFunction<String, Long, Long> r =
        (String label, Long value) -> {
          return value;
        };

    java.util.function.Predicate<String> predicate =
        str -> {
          return str.isEmpty();
        };

    Function<String, BiFunction<String, String, String>> s =
        (String label) -> {
          return (a, b) -> {
            return a + " " + b;
          };
        };
  }
}
