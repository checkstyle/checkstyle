/*
com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.api.fullident;

public class InputFullIdentLiteralNewCondition {
    {
        int j = 20; // violation, 'Unused local variable'
        nested obj = new nested() {
            void method() {
                j += 50;
            }
        };
        obj.getClass();
    }
    class nested {
        protected int j = 21;
    }
}
