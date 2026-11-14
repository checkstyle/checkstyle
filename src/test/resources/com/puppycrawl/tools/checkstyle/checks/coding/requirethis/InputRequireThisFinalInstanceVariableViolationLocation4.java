/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisFinalInstanceVariableViolationLocation4 {
    final int a = 1;
    int other;

    InputRequireThisFinalInstanceVariableViolationLocation4(int a) {
        a += a; // violation '.* variable 'a' needs "this.".'
        other = a = a; // violation '.* variable 'a' needs "this.".'
    }
}

class InputRequireThisFinalInstanceVariableViolationLocation4Helper {
    final int a = 1;

    InputRequireThisFinalInstanceVariableViolationLocation4Helper() {
        int a = 2;
        a = a; // violation '.* variable 'a' needs "this.".'
        {
            a = a; // violation '.* variable 'a' needs "this.".'
        }
    }
}

class InputRequireThisFinalInstanceVariableViolationLocation4Helper2 {
    final int a;
    final int b;

    {
        a = 1;
    }

    {
        b = 2;
    }

    InputRequireThisFinalInstanceVariableViolationLocation4Helper2(int a) {
        a = a; // violation '.* variable 'a' needs "this.".'
    }
}
