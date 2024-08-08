/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughMultipleReliefPatterns {

  void method(int i) {
    while (true) {
      switch (i) {
        case 5: {
          i++;
        }
        /* block */ /* fallthru */ // comment
        case 6:
          i++;
          /* block */ /* fallthru */ // comment

      }
    }
  }
}
