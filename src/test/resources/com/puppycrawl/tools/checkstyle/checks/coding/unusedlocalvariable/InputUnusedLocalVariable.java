/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.util.function.Predicate;

public class InputUnusedLocalVariable {

    int sameName = 20;

    private void sameName(int unusedParameter) {
        int sameName = 10; // violation
        int b = 12; // violation
        b = 23;
        this.sameName /= 2;
    }

    public void testCallingMethods() {
        int var = 21; // ok
        sameName(var);
        var = sameName;
    }

    public void testInLambdas() {
        int test = 21; // ok
        int isInOuterScope = 32; // ok
        Predicate<Integer> foo  = integer -> {
            int b; // violation
            int a = integer; // ok
            b = integer;
            boolean ans = a > 12; // ok
            int c = 21; // violation
            c = -isInOuterScope;
            return ans;
        };
        foo.test(test);
    }
}
