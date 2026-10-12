/*
RedundantThis
checkMethods=(default)false
allowAdjacentToRequiredThis=(default)true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisMultipleInputsFirst {
    private int a;

    public int getA() {
        return this.a;
        // violation above 'Redundant "this", field 'a' can be accessed directly.'
    }
}
