/*
RedundantThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisMethodsOnly {
    private int x;

    public InputRedundantThisMethodsOnly() {
        this.x = x; //  violation
    }

    public void methodOne() {
        methodTwo(); // no violation
    }

    public void methodTwo() {
        methodThree(); // no violation
    }

    public static void methodThree() {}
}
