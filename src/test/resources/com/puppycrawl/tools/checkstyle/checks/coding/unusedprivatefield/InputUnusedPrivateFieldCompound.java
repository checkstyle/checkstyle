/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldCompound {

    private int counter;
    private int total;
    private int unused; // violation, 'Unused private field 'unused''

    public void foo() {
        counter++;
        total += 5;
    }
}
