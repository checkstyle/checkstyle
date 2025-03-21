// non-compiled with javac: Compilable with Java17

package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

/**some javadoc.*/
public class InputSwitchOnStartOfTheLineCorrect {
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
}
