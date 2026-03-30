/*
RedundantThis
checkMethods = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisPatternVariables {
    private Object s;

    void negatedNoAbruptExit1(Object obj) {
        if (!(obj instanceof String s)) {
            log();
        }
        this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
    }

    void negatedNoAbruptExit2(Object obj) {
        if (!(obj instanceof String s)) log();
        this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
    }

    void negatedWithReturn1(Object obj) {
        if (!(obj instanceof String s)) {
            return;
        }
        this.s = s; // ok, pattern variable 's' shadows the field here
    }

    void negatedWithReturn2(Object obj) {
        if (!(obj instanceof String s)) return;
        this.s = 1; // ok, pattern variable 's' shadows the field here
    }

    void positiveInsideThen1(Object obj) {
        if (obj instanceof String s) {
            this.s = s; // ok, pattern variable 's' shadows the field here
        }
        this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
    }

    void positiveInsideThen2(Object obj) {
        if (obj instanceof String s) this.s = s;
        this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
    }

    void positiveAfterIf1(Object obj) {
        if (obj instanceof String s) {
            log();
        }
        this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
    }

    void positiveAfterIf2(Object obj) {
        if (obj instanceof String s) log();
        this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
    }

    void normalIf(Object obj) {
        if (!s.equals(obj)) {
            this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
        }
        if (s.equals(obj)) {
            this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
        }
        if (obj instanceof String) {
            this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
        }
        if (obj instanceof String) this.s = 1;
        // violation above 'Redundant "this", field 's' can be accessed directly'
        if (!(obj instanceof String)) {
            this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
        }
        if (!(obj instanceof String)) this.s = 1;
        // violation above 'Redundant "this", field 's' can be accessed directly'
    }

    void method1(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
        if (!(obj instanceof String s)) {
            return;
        }
        this.s = 1;
    }

    void method2(Object obj) {
        if (obj instanceof String s) {
            this.s = 1;
        }
        else {
            this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
        }
    }

    public void patternMatchingInSwitch(Object obj) {
        switch (obj) {
            case String s -> this.s = 1;
            default -> {
                this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
            }
        }

        switch (obj) {
            case String s -> log();
            case Integer i -> this.s = 1;
            // violation above 'Redundant "this", field 's' can be accessed directly'
            default -> {}
        }
    }

    private void log() {}
}
