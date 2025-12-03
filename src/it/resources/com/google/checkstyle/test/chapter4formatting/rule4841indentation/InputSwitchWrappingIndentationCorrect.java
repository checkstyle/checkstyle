// Java17

package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

import java.util.List;

/** Some javadoc. */
public class InputSwitchWrappingIndentationCorrect {
  String testMethod1(int i) {
    String name = "";
    name =
        switch (i) {
          case 1 -> "one";
          case 2 -> "two";
          default -> "zero";
        };
    return name;
  }

  String testMethod2(int x, int y) {
    return switch (x) {
      case 1 ->
          switch (y) {
            case 2 -> "test";
            default -> "inner default";
          };
      default -> "outer default";
    };
  }

  String testMethod3(List<Integer> operations) {
    return operations.stream()
        .map(
            op ->
                switch (op) {
                  case 1 -> "test";
                  default -> "TEST";
                })
        .findFirst()
        .orElse("defaultValue");
  }

  String testMethod4(int x, int y) {
    return switch (x) {
      case 1 ->
          switch (y) {
            case 2 -> "test";
            default -> "inner default";
          };
      default -> "outer default";
    };
  }
}
