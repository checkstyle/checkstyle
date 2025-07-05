package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

import java.util.List;

/** some javadoc. */
public class InputFormattedSwitchWrappingIndentation {
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

  String testMethod4Invalid(int x, int y) {
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
