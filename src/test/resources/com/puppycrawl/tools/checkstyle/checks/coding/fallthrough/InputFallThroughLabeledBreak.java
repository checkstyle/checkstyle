/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughLabeledBreak {

    public int method(int a, int b) {
        top: switch (a) {
            case 1:
                switch (b) {
                    case 10:
                        break top; // OK
                    default:
                        return 11;
                }
            case 2:
                return 2; // OK
        }

        outer: for (int c = 0; c < 2; c++) {
            switch (a) {
                case 1:
                    if (b == 10) {
                        break outer; // OK
                    }
                    return 11;
                case 2:
                    for (int i = 0; i < 3; i++) {
                        if (i == 2) {
                            break outer; // OK
                        }
                    }
                case 3:  // violation 'Fall through from previous branch of the switch statement.'
                    return 3;
            }
        }

        loop_outer: for (int c = 0; c < 2; c++) {
            outer: switch (a) {
                case 1:
                    inner: switch (b) {
                        case 10:
                            break inner; // OK
                        default:
                            return 11;
                    }
                case 2:  // violation 'Fall through from previous branch of the switch statement.'
                    inner: for (int i = 0; i < 3; i++) {
                        break inner;
                    }
                case 3:  // violation 'Fall through from previous branch of the switch statement.'
                    return 1;
                case 4:
                    switch (b) {
                        case 10:
                            break outer; // OK
                        default:
                            return 11;
                    }
                case 5:
                    return 5;
            }
        }

        return -1;
    }
}
