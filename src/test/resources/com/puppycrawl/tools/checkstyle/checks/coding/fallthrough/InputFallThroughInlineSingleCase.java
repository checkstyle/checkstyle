/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughInlineSingleCase{
  void method(int a) {
    switch (a) {case 1:;}
    // violation above 'Fall\ through from the last branch of the switch statement.'
  }
}
