/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisReturnedVariable {
    int field1 = 0;

    int method(int param) {
        param = field1;
        return param;
    }
}
