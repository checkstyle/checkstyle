package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

import java.util.List;

/**some javadoc.*/
public class InputLambdaChildCorrect {
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
}
