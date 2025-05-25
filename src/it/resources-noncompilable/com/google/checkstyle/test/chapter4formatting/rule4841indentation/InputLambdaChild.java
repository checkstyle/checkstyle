package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

import java.util.List;

/**some javadoc.*/
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
                    // violation above '.* incorrect indentation level 16, expected .* 20.'
                      case 1 -> "test";
                      default -> "TEST";
                    })
        .findFirst().orElse("defaultValue");
  }
}
