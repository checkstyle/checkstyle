// non-compiled with javac: Compilable with Java17

package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

/**some javadoc.*/
public class InputSwitchOnStartOfTheLine {
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
      switch (num) {       // violation '.* incorrect indentation level 6, expected .* 8.'
        case 1, 3, 7 -> 1; // violation '.* incorrect indentation level 8, expected .* 10.'
        case 2, 4, 6 -> 2; // violation '.* incorrect indentation level 8, expected .* 10.'
        default -> 0;      // violation '.* incorrect indentation level 8, expected .* 10.'
      };                   // violation '.* incorrect indentation level 6, expected .* 8.'
  }

  String testMethod4Invalid(int i) {
    String s =
          switch (i) {         // violation '.* incorrect indentation level 10, expected .* 8.'
            case 1 -> "one";   // violation '.* incorrect indentation level 12, expected .* 10.'
            case 2 -> "two";   // violation '.* incorrect indentation level 12, expected .* 10.'
            default -> "zero"; // violation '.* incorrect indentation level 12, expected .* 10.'
        };
    return s;
  }
}
