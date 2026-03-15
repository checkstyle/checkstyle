/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldQualifiedThis {

    private int x;
    private int unused; // violation, 'Unused private field 'unused''

    class Inner {
        void foo() {
            int y = InputUnusedPrivateFieldQualifiedThis.this.x;
        }
    }
}
