package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

/*
* Config
* checkFields = true
* checkMethods = false
*/
public class InputRedundantThisFieldsOnly {
    private int x;
    private static int y;

    public InputRedundantThisFieldsOnly() {
        this.x = x; // violation - redundant 'this' qualifier
        method(); // no violation - methods are not checked
    }

    public InputRedundantThisFieldsOnly(int x) {
        this.x = x; // no violation - 'this' is necessary here
        this.method();
    }

    public int method() {
        return this.y++; // violation - redundant 'this' qualifier
    }
}
