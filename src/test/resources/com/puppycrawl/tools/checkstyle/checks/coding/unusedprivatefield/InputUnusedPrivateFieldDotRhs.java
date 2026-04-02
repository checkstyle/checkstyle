/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldDotRhs {

    private int unused; // violation, 'Unused private field 'unused''

    class Inner {
        private int source;
    }

    public void foo() {
        Inner obj = new Inner();
        int target;
        target = obj.source;
    }
}
