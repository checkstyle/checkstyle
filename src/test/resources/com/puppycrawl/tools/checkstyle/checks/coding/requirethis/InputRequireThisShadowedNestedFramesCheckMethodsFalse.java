/*
RequireThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisShadowedNestedFramesCheckMethodsFalse {
}

class Issue22403 {
    int i;
    void foo() {
        i++; // violation 'Reference to instance variable 'i' needs "this.".'
        i++; int i = 1; i++; // violation 'Reference to instance variable 'i' needs "this.".'
        instanceMethod();
    }
    void instanceMethod() {};

    class Nested {
        void bar() {
            instanceMethod();
            i++; // violation 'Reference to instance variable 'i' needs "Issue22403.this.".'
        }
    }
}

class Issue25393{
    void foo(int i) {}
    static void foo(double i) {}
    void foo() {}

    void bar() {
        foo(1);
        foo();
    }
}

class NestedRechange3 {
    final String s = "";

    NestedRechange3() {
        String s = "t";
        s = s.substring(0); // violation 'Reference to instance variable 's' needs "this.".'
    }

    private static class NestedStatic {
        static final String s = "";

        public void method() {
            s.substring(0);
        }
    }
}

class NestedFrames3 {
    int a = 0;
    int b = 0;

    public int oneReturnInMethod2() {
        for (int i = 0; i < 10; i++) {
            int a = 1;
            if (a != 2 && true) {
                if (true | false) {
                    if (a - a != 0) {
                        a += 1;
                    }
                }
            }
        }
        return a + a * a;
        // 3 violations above:
        //    'Reference to instance variable 'a' needs "this.".'
        //    'Reference to instance variable 'a' needs "this.".'
        //    'Reference to instance variable 'a' needs "this.".'
    }

    public int oneReturnInMethod3() {
        for (int b = 0; b < 10; b++) {
        }
        return b + b * b;
        // 3 violations above:
        //    'Reference to instance variable 'b' needs "this.".'
        //    'Reference to instance variable 'b' needs "this.".'
        //    'Reference to instance variable 'b' needs "this.".'
    }
    final NestedFrames NestedFrames = new NestedFrames();
}
