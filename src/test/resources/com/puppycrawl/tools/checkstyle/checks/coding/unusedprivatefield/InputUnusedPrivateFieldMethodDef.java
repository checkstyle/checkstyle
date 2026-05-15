/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldNames = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.io.Serial;

public class InputUnusedPrivateFieldMethodDef {
    private int used; // ok, private field is used

    private void firstMethod() {
        int used = 1;
        System.out.println(used);
    }

    private void secondMethod() {
        System.out.println(used);
    }

    @Serial
    private static final long serialVersionUID = 1434589190483306227L;

}
