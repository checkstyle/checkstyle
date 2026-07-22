/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

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


    class Inner {
        Inner(InputUnusedPrivateField4 InputUnusedPrivateField4.this) {
        }
    }

    void m(InputUnusedPrivateField4 this) { }

}
