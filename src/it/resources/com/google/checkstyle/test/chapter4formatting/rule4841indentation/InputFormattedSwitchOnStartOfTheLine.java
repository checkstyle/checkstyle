// Java17

package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** Some javadoc. */
public class InputFormattedSwitchOnStartOfTheLine {
  String testMethod1(int i) {
    String s =
        switch (i) {
          case 1 -> "one";
          case 2 -> "two";
          default -> "zero";
        };
    return s;
  }

  void testMethod2(int month) {
    int result =
        switch (month) {
          case 1, 6, 7 -> 3;
          case 2, 9, 10, 11, 12 -> 1;
          case 3, 5, 4, 8 -> {
            yield month * 4;
          }
          default -> 0;
        };
  }

  void testMethod3Invalid(int num) {
    int odd =
        switch (num) {
          case 1, 3, 7 -> 1;
          case 2, 4, 6 -> 2;
          default -> 0;
        };
  }

  String testMethod4Invalid(int i) {
    String s =
        switch (i) {
          case 1 -> "one";
          case 2 -> "two";
          default -> "zero";
        };
    return s;
  }
}
