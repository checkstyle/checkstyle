/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldSameName {

    class Inner1 {
        private int count; // violation, 'Unused private field 'count''
    }

    class Inner2 {
        private int count;
        void foo() {
            count++;
        }
    }
}
