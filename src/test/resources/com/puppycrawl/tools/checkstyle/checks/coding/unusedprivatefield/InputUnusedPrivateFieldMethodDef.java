/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldMethodDef {
    private int used; // ok, as b is used

    private void firstMethod() {
        int used = 1;
        System.out.println(used);
    }

    private void secondMethod() {
        System.out.println(used);
    }
}
