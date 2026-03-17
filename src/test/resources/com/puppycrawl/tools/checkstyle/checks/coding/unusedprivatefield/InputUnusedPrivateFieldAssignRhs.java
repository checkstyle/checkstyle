/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAssignRhs {

    private int source;
    private int unused; // violation, 'Unused private field 'unused''

    public void foo() {
        int target;
        target = source;
    }
}
