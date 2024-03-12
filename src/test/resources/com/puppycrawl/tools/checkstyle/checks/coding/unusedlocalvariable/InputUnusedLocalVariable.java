/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.util.function.Predicate;

public class InputUnusedLocalVariable {

    int sameName = 20;

    static int a = 12;

    static int b = 12;

    String s;

    Obj obj = new Obj() {
        int a = 12;
        int b = 12;
    };

    private void sameName(int unusedParameter) {
        int sameName = 10; // violation
        int b = 12; // violation
        b = 23;
        this.sameName /= 2;
        int testInLambdas = 0; // violation
        testInLambdas = 12;
        int coding = 0; // violation
        int InputUnusedLocalVariable = 1; // violation
        com.puppycrawl.tools.checkstyle.checks
                .coding.unusedlocalvariable
                .InputUnusedLocalVariable.testInLambdas(a);
    }

    public void testCallingMethods() {
        int var = 21;
        sameName(var);
        var = sameName;
    }

    public static void testInLambdas(int q) {
        int test = 21;
        int isInOuterScope = 32;
        Predicate<Integer> foo = integer -> {
            int b; // violation
            int a = integer;
            b = integer;
            boolean ans = a > 12;
            int c = 21; // violation
            c = -isInOuterScope;
            return ans;
        };
        foo.test(test);
    }

    public void testMultipleIdentifiers() {
        int[] arr = {1};
        int a = 21;
        arr[0] = a;
        Class<Integer> b = int.class; // violation
        @SuppressWarnings("unused")
        int c = 41; // violation
    }

    public void testChainedCalls() throws InterruptedException {
        Obj p; // violation
        Obj q = null;
        p = q.foo().p;
        if (s instanceof String) {
        }
        Boolean r = false;
        while (r.booleanValue() != true) {
        }
        int a = 13, b = 21;
        q.getClass().wait(a, b);
        int f = 12; // violation
        Predicate<String> obj = InputUnusedLocalVariable::f;
        obj.test("test");
        int foo = 12; // violation
        foo(q);
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

    Object foo(Object a) {
        return a;
    }

    public static boolean f(String a) {

        class k { // local class not to be added to "classes" stack
            int a = 12;
        }
        return true;
    }

}
