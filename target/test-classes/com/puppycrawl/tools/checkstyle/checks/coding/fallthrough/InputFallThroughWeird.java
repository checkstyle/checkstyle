/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)

*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughWeird {
        void foo(int i){
        switch(i) {
            case 1:
                 int fallthru = 2; // some comment
            case 2: // violation 'Fall\ through from previous branch of the switch statement.'
                 break;
            case 3:
                 int c = 2; // fall thru
            case 4: // should be ok
                break;
            case 5:
                 String x = "x"; // fall thru
            case 6: // should be ok
            default:
            }
    }
}
