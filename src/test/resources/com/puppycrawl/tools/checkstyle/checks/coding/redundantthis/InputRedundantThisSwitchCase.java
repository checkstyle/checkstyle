/*
RedundantThis
checkMethods=(default)false
allowAdjacentToRequiredThis=(default)true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisSwitchCase {
    private int min;
    private int max;

    int lastInCaseGroup(int kind, int min) {
        switch (kind) {
            case 1:
                this.min = min;
                return this.max;
            default:
                // violation below 'Redundant "this", field 'max' can be accessed directly.'
                return this.max;
        }
    }
}
