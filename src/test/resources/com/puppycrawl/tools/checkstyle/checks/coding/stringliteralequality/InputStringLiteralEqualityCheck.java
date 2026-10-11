/*
StringLiteralEquality


*/

package com.puppycrawl.tools.checkstyle.checks.coding.stringliteralequality;

public class InputStringLiteralEqualityCheck {

    int method(int a) {
        if (a == 2 + 3
                + 5 + 2) {
            return 0;
        }
        else {
            return 1;
        }
    }

    boolean method() {
        boolean result = false;
        if (result == false && 3 - 0 == 9) {
        }
        return result;
    }

    boolean method2() {
        boolean result = false;
        String str = "check";
        if (result == false && 3 - 0 == 9
                && str == "check" + "Style"
                // violation above 'Literal Strings should be compared using equals(), not '==''
                + "code" + "convention") {
        }
        return result;
    }
}
