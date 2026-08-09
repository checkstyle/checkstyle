/*
RedundantThis
checkMethods = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisPatternVariables2 {
    private Object s;

    void differentPatternVariableName(Object obj) {
        if (!(obj instanceof String other)) {
            return;
        }
        this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
    }

    void insideThenBranchOfNegatedWithReturn(Object obj) {
        if (!(obj instanceof String s)) {
            this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
            return;
        }
    }

    void positiveWithReturn(Object obj) {
        if (obj instanceof String s) {
            return;
        }
        this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
    }

    void nonImmediateSiblingNegated(Object obj) {
        if (!(obj instanceof String s)) {
            return;
        }
        int dummy = 0;
        this.s = s;
    }

    static class Base {
        protected Object s;
    }

    void localClassBoundary(Object obj) {
        if (!(obj instanceof String s)) {
            return;
        }
        class Local extends Base {
            void inner() {
                this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
            }
        }
    }

    void colonSwitchCase(Object obj) {
        switch (obj) {
            case String s:
                this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
                break;
            default:
                break;
        }
    }

    void insideThenBranchOfNegatedNoReturn(Object obj) {
        if (!(obj instanceof String s)) {
            this.s = 1; // violation 'Redundant "this", field 's' can be accessed directly'
        }
    }
}
