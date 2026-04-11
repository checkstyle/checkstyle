package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** Some javadoc. */
public class InputLineBreakAfterLeftCurlyOfBlockInSwitch {

  void testMethod1(int month) {
    int result =
        switch (month) {
          case 1, 6, 7 -> {
            System.out.println("try");
            yield 1;
          }
          // violation below ''{' at column 36 should have line break after'
          case 2, 9, 10, 11, 12 -> { yield 2; }
          // violation below ''{' at column 30 should have line break after'
          case 3, 5, 4, 8 -> { yield month << 2; }
          default -> 0;
        };
  }

  void testMethod2(int number) {
    switch (number) {
      case 0, 1 -> System.out.println("0");
      case 2 ->
          handleTwoWithAnExtremelyLongMethodCallThatWouldNotFitOnTheSameLine();
      // violation below ''{' at column 18 should have line break after'
      default -> { handleSurprisingNumber(number); }
    }
  }

  private static int handleSurprisingNumber(int number) {
    return number;
  }

  private int handleTwoWithAnExtremelyLongMethodCallThatWouldNotFitOnTheSameLine() {
    return 0;
  }
}
