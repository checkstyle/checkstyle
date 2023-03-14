/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughLabeledBreak {

    int method(int a, int b) {
        top: switch (a) {
        case 1:
            switch (b) {
            case 10:
                break top; // ok
            default:
                return 11;
            }
        case 2:
            return 2;
        }

        outer: outer2: switch (a) {
            case 1:
                inner1: inner2: switch (b) {
                case 10:
                    break outer; // ok
                default:
                    return 11;
                }
            case 2:
                inner: for (;;) {
                    break outer2; // ok
                }
            case 3:
                return 2;
        }

        outer: switch (a) {
        case 1:
            inner: inner2: switch (b) {
            case 10:
                break inner;
            default:
                return 11;
            }
        case 2: // violation 'Fall through from previous branch of the switch statement.'
            inner: inner2: for (;;) {
                break inner2;
            }
        case 3: // violation 'Fall through from previous branch of the switch statement.'
            return 1;
        case 4:
            switch (b) {
            case 10:
                break /* comment */ outer; // ok
            default:
                return 11;
            }
        case 5:
            outer1: { switch(a) {
                case 1: break outer1;
            }}
        case 6: // violation 'Fall through from previous branch of the switch statement.'
            outer1: { switch(a) {
                case 1: break outer; // ok
            }}
        case 7:
            return 1;
        }

        return -1;
    }
}
