package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

/**
 * Config
 * default
 */
public class InputRequireThisFinalInstanceVariable {
    final int x = 1;
    final int y = 2;
    final int z;

    public InputRequireThisFinalInstanceVariable(int y, int z) {
        y = y; // violation until #8973 is fixed
        z = z; // violation until #8973 is fixed
    }

    {
        this.z = 3;
    }

    public void foo(int x) {
        x = x; // ok
    }
}
