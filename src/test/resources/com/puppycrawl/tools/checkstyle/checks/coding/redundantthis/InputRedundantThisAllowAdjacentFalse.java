/*
RedundantThis
checkMethods=(default)false
allowAdjacentToRequiredThis=false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisAllowAdjacentFalse {
    private int x;
    private int y;

    public InputRedundantThisAllowAdjacentFalse(int x) {
        this.x = x;
        this.y = 1;
        // violation above 'Redundant "this", field 'y' can be accessed directly.'
    }
}
