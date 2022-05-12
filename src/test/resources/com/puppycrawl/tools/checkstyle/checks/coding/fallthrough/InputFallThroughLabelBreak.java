/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughLabelBreak
{
    int method(int a, int b) {
        top: switch (a) {
            case 1:
                switch (b) {
                    case 10:
                        break top;
                    default:
                        return 11;
                }
            case 2: // no violation
                return 2;
        }
        return -1;
    }

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
