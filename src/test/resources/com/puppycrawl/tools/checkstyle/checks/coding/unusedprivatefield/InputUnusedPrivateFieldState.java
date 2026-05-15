/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldNames = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

class InputUnusedPrivateFieldState {
    private int a; // violation 'Unused private field'
}

class SecondClass extends InputUnusedPrivateFieldState {
    private int used; // ok, private field is used

    int method() {
        return used;
    }
}
