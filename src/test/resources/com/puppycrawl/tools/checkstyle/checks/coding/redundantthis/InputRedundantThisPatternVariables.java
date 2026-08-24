/*
RedundantThis
checkMethods=(default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisPatternVariables {
    private Object s;

    // Pattern varibales are ignored in RedundantThisCheck.
    void positive(Object obj) {
        if (obj instanceof String s) {
            this.s = s;
        }
        this.s = s;
    }
    
    void negated(Object obj) {
        if (!(obj instanceof String s)) {
            this.s = s;
            return;
        }
        this.s = s;
    }
}
