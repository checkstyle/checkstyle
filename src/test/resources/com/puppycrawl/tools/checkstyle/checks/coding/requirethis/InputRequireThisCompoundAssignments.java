/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisCompoundAssignments {
    private int value;
    private boolean enabled;

    InputRequireThisCompoundAssignments(int value) {
        value += 1; // violation 'variable 'value' needs "this."'
        value -= 1; // violation 'variable 'value' needs "this."'
        value |= 1; // violation 'variable 'value' needs "this."'
    }

    InputRequireThisCompoundAssignments(int value, boolean enabled) {
        this.value -= 1;
        this.value |= 1;
        this.enabled |= enabled;
    }

    void update(int value) {
        value += 1; // violation 'variable 'value' needs "this."'
        value -= 1; // violation 'variable 'value' needs "this."'
        value |= 1; // violation 'variable 'value' needs "this."'
    }

    void updateGood(int value) {
        this.value -= value;
        this.value |= value;
    }

    void updateBoolean(boolean enabled) {
        enabled |= false; // violation 'variable 'enabled' needs "this."'
    }

    void updateBooleanGood(boolean enabled) {
        this.enabled |= false;
    }

    void local() {
        int value = 1;
        value += value; // violation 'variable 'value' needs "this."'
        value -= value; // violation 'variable 'value' needs "this."'
        value |= value; // violation 'variable 'value' needs "this."'
    }

    void localGood() {
        int value = 1;
        this.value -= value;
        this.value |= value;
    }

    static void staticContext(int value) {
        value -= 1;
        value |= 1;
    }

    void noOverlap(int other) {
        other -= 1;
        other |= 1;
    }
}
