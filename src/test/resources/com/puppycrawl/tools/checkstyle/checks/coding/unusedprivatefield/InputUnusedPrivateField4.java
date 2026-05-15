/*
UnusedPrivateField
checkUnusedPrivateField = (default)true

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateField4 {

    private int copyfield;

    private int usedField;

    void setCopyfield() {
        int copy = copyfield;
    }

    void useField() {
        System.out.println(usedField);
    }
}
