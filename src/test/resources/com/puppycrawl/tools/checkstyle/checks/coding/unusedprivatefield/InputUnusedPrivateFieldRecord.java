/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldRecord {

    record MyRecord(int x) {
        private static int unused; // violation, 'Unused private field 'unused''
        private static int used;
        static int foo() { return used; }
    }
}
