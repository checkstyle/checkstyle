/*
EmptyLineSeparator
allowEmptyLineBeforeReturn = true



*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorBeforeReturn {
    boolean test(){
        int a = 6;
        int b = 6;




        return a == b; // ok
    }

    boolean isEquals4() {
        int result = calculate();
        return result == 4; // violation ''return' should be separated from previous line.'
    }

    int calculate() {

        return 2 + 2; // ok
    }
}



