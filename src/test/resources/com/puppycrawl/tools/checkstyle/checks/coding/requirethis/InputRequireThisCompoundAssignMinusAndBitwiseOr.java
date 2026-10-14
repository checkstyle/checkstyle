/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisCompoundAssignMinusAndBitwiseOr {

    private int value;
    private int flags;
    private boolean active;

    // '-=' overlapping by argument — should flag 'value'
    void subtractByArg(int value) {
        value -= 1; // violation 'Reference to instance variable 'value' needs "this.".'
    }

    // '|=' overlapping by argument — should flag 'flags'
    void orByArg(int flags) {
        flags |= 0x01; // violation 'Reference to instance variable 'flags' needs "this.".'
    }

    // '+=' overlapping by argument — already detected (control case)
    void addByArg(int value) {
        value += 1; // violation 'Reference to instance variable 'value' needs "this.".'
    }

    // '-=' overlapping in constructor
    InputRequireThisCompoundAssignMinusAndBitwiseOr(int value) {
        value -= 10; // violation 'Reference to instance variable 'value' needs "this.".'
        this.flags = 0;
        this.active = false;
    }

    // '|=' overlapping in constructor
    InputRequireThisCompoundAssignMinusAndBitwiseOr(int value, int flags) {
        this.value = value;
        flags |= 0xFF; // violation 'Reference to instance variable 'flags' needs "this.".'
        this.active = false;
    }

    // no overlap — no parameter shadows field
    void noOverlap() {
        value -= 1;
        flags |= 0x02;
    }

    // qualified this — none expected
    void qualifiedThis(int value, int flags) {
        this.value -= value;
        this.flags |= flags;
    }

    // local variable shadows field — compound assign on local
    void localShadow() {
        int value = 100;
        value -= 50;
    }

    // overlapping by argument on Long (autoboxing)
    void subtractByArgLong(Long value) {
        value -= 1L; // violation 'Reference to instance variable 'value' needs "this.".'
    }

    // overlapping by argument on boolean |=
    void booleanOrByArg(boolean active) {
        active |= true; // violation 'Reference to instance variable 'active' needs "this.".'
    }

    // static context — no 'this' possible
    private static int staticField;

    static void staticMethod(int staticField) {
        staticField -= 1;
        staticField |= 0x10;
    }
}
