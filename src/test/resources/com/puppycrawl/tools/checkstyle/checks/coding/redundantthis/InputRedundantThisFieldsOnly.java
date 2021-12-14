/*
RedundantThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;
public class InputRedundantThisFieldsOnly {
    private int x;
    private static int y;

    public InputRedundantThisFieldsOnly() {
        this.x = x; // violation
        method(); // no violation - methods are not checked
    }

    public InputRedundantThisFieldsOnly(int x) {
        this.x = x; // no violation - 'this' is necessary here
        method(); // no violation
    }

    public int method() {
        return this.y++; // violation
    }
}
