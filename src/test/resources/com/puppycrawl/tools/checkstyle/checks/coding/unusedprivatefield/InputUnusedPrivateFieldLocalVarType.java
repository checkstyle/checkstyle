/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldLocalVarType {

    private int value; // violation 'Unused private field'

    static class Other {
        int value;
    }

    void m() {
        Other local = new Other();
        System.out.println(local.value);
    }
}
