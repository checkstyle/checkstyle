/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughLabeledBreak {

    public int test(int a, int b) {
        top: switch (a) {
            case 1:
                switch (b) {
                    case 10:
                        break top;
                    default:
                        return 11;
                }
            case 2:
                return 2;
        }
        return -1;
    }
}
