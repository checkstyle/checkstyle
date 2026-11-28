/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisFinalInstanceVariableViolationLocation {
    final int z;
    final int y = 2;
    int v;

    public InputRequireThisFinalInstanceVariableViolationLocation(int z, int y, int v) {
        z = z; // violation '.* variable 'z' needs "this.".'
        v = v; // violation '.* variable 'v' needs "this.".'
        if (y > 0) {
            y = y; // violation '.* variable 'y' needs "this.".'
        }
    }

    {
        z = 2;
    }
}

class InputRequireThisFinalInstanceVariableViolationLocationHelper {
    final int w;

    InputRequireThisFinalInstanceVariableViolationLocationHelper(int w) {
        this();
        w = w; // violation '.* variable 'w' needs "this.".'
    }

    InputRequireThisFinalInstanceVariableViolationLocationHelper() {
        w = 5;
    }
}
