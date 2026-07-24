/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldNames = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.io.Serial;

public class InputUnusedPrivateField4 {

    private int copyfield; // ok, private field is used

    private int usedField; // ok, private field is used

    @Serial
    private static final long serialVersionUID = 1434589190483306227L;

    void setCopyfield() {
        int copy = copyfield; // ok, as it is not field
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
