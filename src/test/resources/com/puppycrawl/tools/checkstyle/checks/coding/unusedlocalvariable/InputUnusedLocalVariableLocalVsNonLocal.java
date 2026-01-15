/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

class SuperClass {
    int superField = 100;
}

public class InputUnusedLocalVariableLocalVsNonLocal {

    // Field-level (non-local) anonymous inner class
    SuperClass fieldObj = new SuperClass() {
        {
            int unused1 = superField; // violation, 'Unused local variable'
        }
    };

    public void testMethod() {
        // Local (method-level) anonymous inner class
        SuperClass localObj = new SuperClass() {
            {
                int unused2 = superField; // violation, 'Unused local variable'
            }
        };
        localObj.toString();
    }
}

