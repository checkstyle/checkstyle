// CHECKSTYLE.OFF: LeftCurly - this file tests other checks, not LeftCurly

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
          case 2, 9, 10, 11, 12 -> { yield 2; } // false-negative, ok until #17565
          case 3, 5, 4, 8 -> { yield month << 2; } // false-negative, ok until #17565
          default -> 0;
        };
  }

  void testMethod2(int number) {
    switch (number) {
      case 0, 1 -> System.out.println("0");
      case 2 ->
          handleTwoWithAnExtremelyLongMethodCallThatWouldNotFitOnTheSameLine();
      default -> { handleSurprisingNumber(number); } // false-negative, ok until #17565
    }
  }

  private static int handleSurprisingNumber(int number) {
    return number;
  }

  private int handleTwoWithAnExtremelyLongMethodCallThatWouldNotFitOnTheSameLine() {
    return 0;
  }
}
