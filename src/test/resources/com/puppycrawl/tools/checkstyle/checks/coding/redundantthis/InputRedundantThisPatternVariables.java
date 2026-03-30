/*
RedundantThis
checkMethods=(default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisPatternVariables {
    private Object s;

    // Pattern varibales are ignored in RedundantThisCheck.
    void positiveAfterIf1(Object obj) {
        if (obj instanceof String s) {
            this.s = s;
            // violation above 'Redundant "this", field 's' can be accessed directly.'
        }
    }

    void negatedWithReturn1(Object obj) {
        if (!(obj instanceof String s)) {
            return;
        }
        this.s = s;
        // violation above 'Redundant "this", field 's' can be accessed directly.'
    }
}
