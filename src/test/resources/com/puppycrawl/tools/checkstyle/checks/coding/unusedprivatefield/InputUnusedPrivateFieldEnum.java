/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldEnum {

    enum MyEnum {
        A;
        private int unused; // violation, 'Unused private field 'unused''
        private int used;
        void foo() { System.out.println(used); }
    }
}
