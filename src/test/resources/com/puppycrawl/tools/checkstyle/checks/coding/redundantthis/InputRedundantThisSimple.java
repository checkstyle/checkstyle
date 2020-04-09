package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

/*
* Config = default
*/
public class InputRedundantThisSimple {
    private int x;

    public InputRedundantThisSimple() {
        this.x = x; // violation - redundant 'this' qualifier
    }

    public InputRedundantThisSimple(int x) {
        this.x = x; // no violation - 'this' is necessary here
    }

    public int methodOne() {
        this.methodTwo(5); // violation - redundant 'this' qualifier
        methodTwo(10);
        return this.x++; // violation - redundant 'this' qualifier
    }

    public void methodTwo(int x) {
        this.methodThree(); // violation - redundant 'this' qualifier
        this.x++; // no violation - 'this' is necessary here
    }

    public static void methodThree() {}

    public int methodFour() {
        int x = 5;
        return this.x + x ; // no violation - 'this' is necessary here
    }

    private static int y;

    public void methodFive() {
        this.y = 0; // violation - redundant 'this' qualifier
    }
}
