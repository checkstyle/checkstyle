/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisCompoundAssignmentsAllFields {
    private int value;

    InputRequireThisCompoundAssignmentsAllFields() {
        value -= 1; // violation 'variable 'value' needs "this."'
        value |= 1; // violation 'variable 'value' needs "this."'
    }

    InputRequireThisCompoundAssignmentsAllFields(int initial) {
        this.value -= initial;
        this.value |= initial;
    }

    void update() {
        value -= 1; // violation 'variable 'value' needs "this."'
        value |= 1; // violation 'variable 'value' needs "this."'
    }

    void updateGood() {
        this.value -= 1;
        this.value |= 1;
    }

    static void staticContext(int value) {
        value -= 1;
        value |= 1;
    }
}
