/*
RedundantThis
checkMethods=(default)false
allowAdjacentToRequiredThis=(default)true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisMultipleInputsSecond {
    private int x;

    public void method() {
        this.x = 1;
        // violation above 'Redundant "this", field 'x' can be accessed directly.'
    }
}
