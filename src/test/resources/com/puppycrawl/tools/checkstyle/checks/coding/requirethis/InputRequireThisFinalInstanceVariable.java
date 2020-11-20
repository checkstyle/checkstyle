package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

/**
 * Config
 * default
 */
public class InputRequireThisFinalInstanceVariable {
    final int x = 0;
    final int y = 1;
    final int z;

    public InputRequireThisFinalInstanceVariable(int y, int z) {
        y = y; // violation until #8973 is fixed
        z = z; // violation until #8973 is fixed
    }

    {
        this.z = 2;
    }

    public void foo(int x) {
        x = x; // ok
    }
}
