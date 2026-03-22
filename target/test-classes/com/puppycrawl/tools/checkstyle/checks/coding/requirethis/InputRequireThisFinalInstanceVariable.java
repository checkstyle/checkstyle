/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisFinalInstanceVariable {
    final int x = 1;
    final int y = 2;
    final int z;

    public InputRequireThisFinalInstanceVariable(int y, int z) {
        y = y; // violation until #8973 is fixed // violation '.* variable 'y' needs "this.".'
        z = z; // violation until #8973 is fixed // violation '.* variable 'z' needs "this.".'
    }

    {
        this.z = 3;
    }

    public void foo(int x) {
        x = x;
    }
}
