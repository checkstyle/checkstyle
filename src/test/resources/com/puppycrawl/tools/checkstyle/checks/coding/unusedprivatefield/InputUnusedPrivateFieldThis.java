/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldThis {

    private int readViaThis;
    private int writeOnlyViaThis; // violation, 'Unused private field 'writeOnlyViaThis''
    private int readAndWriteViaThis;

    public void foo() {
        this.readViaThis = 5;
        int x = this.readViaThis;
        this.writeOnlyViaThis = 10;
        this.readAndWriteViaThis = 1;
        int y = this.readAndWriteViaThis;
    }
}
