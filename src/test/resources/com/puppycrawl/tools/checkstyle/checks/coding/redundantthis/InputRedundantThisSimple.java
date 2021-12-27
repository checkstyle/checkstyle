/*
RedundantThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisSimple {
    private int x;

    public InputRedundantThisSimple() {
        this.x = x; // violation
    }

    public InputRedundantThisSimple(int x) {
        this.x = x; // no violation
    }

    public int methodOne() {
        methodTwo(5); // no violation
        methodTwo(10);
        return this.x++; // violation
    }

    public void methodTwo(int x) {
        methodThree(); //no  violation
        this.x++; // no violation
    }

    public static void methodThree() {}

    public int methodFour() {
        int x = 5;
        return this.x + x ; // no violation - 'this' is necessary here
    }

    private static int y;

    public void methodFive() {
        this.y = 0; // violation
    }
}
