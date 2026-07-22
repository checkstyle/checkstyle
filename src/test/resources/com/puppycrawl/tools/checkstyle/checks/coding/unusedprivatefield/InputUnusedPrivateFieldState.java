/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

class InputUnusedPrivateFieldState {
    private int a; // violation, 'Unused private field'
}

class SecondClass extends InputUnusedPrivateFieldState {
    private int used;  // ok, as used below

    int method() {
        return used;
    }
}
