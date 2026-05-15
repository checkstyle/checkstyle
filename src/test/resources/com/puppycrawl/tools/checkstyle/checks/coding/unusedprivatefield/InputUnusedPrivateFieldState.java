/*
UnusedPrivateField
checkUnusedPrivateField = (default)true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

class InputUnusedPrivateFieldState {
    private int a; // violation, unused private field
}

class SecondClass extends InputUnusedPrivateFieldState {
    private int used;

    int method() {
        return used;
    }
}
