/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.util.function.Predicate;

public class InputUnusedLocalVariable {

    int sameName = 20;

    int a = 12;

    String s;

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

    public void testIncrementAndDecrementKinds() {
        int a = 0; // violation
        a = ++this.a;
        a++;
        a--;
    }

    public void testMultipleIdentifiers() {
        int[] arr = {1};
        int a = 21;
        arr[0] = a;
        Class<Integer> b = int.class; // violation
    }

    public void testChainedCalls() {
        Obj p; // violation
        Obj q = null; // ok
        p = q.foo().p;
        if(s instanceof String) {
        }
    }

    class SubClass extends InputUnusedLocalVariable {

        void testSuperKeyword() {
            int a = 12; // violation
            a = super.a;
        }
    }

    class Obj {
        Obj p;
        Obj foo() {
            return new Obj();
        }
    }
}
