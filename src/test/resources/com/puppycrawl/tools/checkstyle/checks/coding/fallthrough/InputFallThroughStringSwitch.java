/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughStringSwitch
{
    int method(String arg) {
        int i = 0;
        switch (arg) {
        case "ok": // ok
        case "break":
            break;
        case "violation":
            i++;
        case "fallthru": // violation 'Fall through from previous branch of the switch statement.'
      }
      return i;
   }
}
