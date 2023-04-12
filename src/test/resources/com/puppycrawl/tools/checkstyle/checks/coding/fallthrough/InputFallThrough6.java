/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough6 {
    void foo(int i){
        switch(i) {
            case 1:
                 int fallthru = 2; // fallthru
            case 2: // ok
                 int fallthrough = 2; //            fall thru
            case 3: // ok
                 break;
            case 4:
                 int fallthru2 = 3;
                 //     fall   -   thru
            case 5: // violation 'Fall through from previous branch of the switch statement.'
                 int fallthru3 = 4; // This is a fallthru comment
            case 6: // ok
                 int fallthru4 = 5; // falldown
            default: // violation 'Fall through from previous branch of the switch statement.'
            }
    }
}
