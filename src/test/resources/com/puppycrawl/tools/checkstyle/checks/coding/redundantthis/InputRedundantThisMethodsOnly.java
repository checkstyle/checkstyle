package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

/*
* Config
* checkFields = false
* checkMethods = true
*/
public class InputRedundantThisMethodsOnly {
    private int x;

    public InputRedundantThisMethodsOnly() {
        this.x = x; // no violation - fields are not checked
    }

    public void methodOne() {
        this.methodTwo(); // violation - redundant 'this' qualifier
    }

    public void methodTwo() {
        this.methodThree(); // violation - redundant 'this' qualifier
    }

    public static void methodThree() {}
}
