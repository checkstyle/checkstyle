package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**Some javadoc.*/
public class InputLambdaChild {
  String testMethod1(List<Integer> operations) {
    return operations.stream()
        .map(
                op ->
                    switch (op) {
                      case 1 -> "test";
                      default -> "TEST";
                    })
        .findFirst().orElse("defaultValue");
  }

  String testMethod2(List<Integer> operations) {
    return operations.stream()
        .map(
                op ->
               switch (op) {
               // violation above '.* expected level should be one of the following: 14, 16, 20.'
                      case 1 -> "test";
                      default -> "TEST";
                    })
        .findFirst().orElse("defaultValue");
  }

  // 2 violations 4 lines below:
  // ''{' at column 56 should have line break after.'
  // ''}' at column 76 should be alone on a line.'
  void main(String[] args) {
    group((Function<Integer, Integer>) x -> switch (x) { default: yield x; },
          (Function<Integer, Integer>) x -> switch (x) { default: yield x; });
  }
  // 2 violations 2 lines above:
  // ''{' at column 56 should have line break after.'
  // ''}' at column 76 should be alone on a line.'

  List<String> getThrowsTrees(Object input) {
    return getBlockTags(input,
        kind -> switch (kind) { case "EXCEPTION", "THROWS" -> true; default -> false; },
        String.class);
  }
  // 3 violations 3 lines above:
  // ''{' at column 31 should have line break after.'
  //  'Only one statement per line allowed.'
  // ''}' at column 87 should be alone on a line.'

  void group(Function<Integer, Integer> f1, Function<Integer, Integer> f2) {
    // Dummy method to test syntax/indentation
  }

  <T> List<T> getBlockTags(Object input, Predicate<String> filter, Class<T> type) {
    return List.of();
  }
}
