/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisFinalInstanceVariableViolationLocation2 {
    final int a = 1;
    final int b;
    int other;
    InputRequireThisFinalInstanceVariableViolationLocation2Helper helper;

    {
        Runnable runnable = () -> {
        };
        other = 1;
        this.other = 2;
        helper.w = 7;
        b = 3;
    }

    InputRequireThisFinalInstanceVariableViolationLocation2(int b) {
        b = b; // violation 'Reference to instance variable 'b' needs "this.".'
        b = b + 1; // violation 'Reference to instance variable 'b' needs "this.".'
    }
}

class InputRequireThisFinalInstanceVariableViolationLocation2Helper {
    int w;
}
