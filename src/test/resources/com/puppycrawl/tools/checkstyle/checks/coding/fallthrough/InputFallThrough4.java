/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThrough4
{
    void foo(int i){
        switch(i) {
            case 1:
                 int fallthru = 2; //          some comment
            case 2: // violation 'Fall through from previous branch of the switch statement.'
                 break;
            default:
            }
    }
}
