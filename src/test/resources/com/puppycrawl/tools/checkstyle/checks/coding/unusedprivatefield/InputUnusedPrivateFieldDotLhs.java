/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldDotLhs {

    class Inner {
        private int source; // violation, 'Unused private field 'source''
    }

    public void foo() {
        Inner obj = new Inner();
        obj.source = 1;
    }
}

