/*
RequireThis
checkFields = false
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisShadowedNestedFrames {
}

class Issue22402 {
    int i;
    void foo() {
        i++;
        i++; int i = 1; i++;
        instanceMethod(); // violation 'Method call to 'instanceMethod' needs "this.".'
    }
    void instanceMethod() {};

    class Nested {
        void bar() {
            instanceMethod(); // violation 'Method .* 'instanceMethod' needs "Issue22402.this.".'
            i++;
        }
    }
}

class Issue25392{
    void foo(int i) {}
    static void foo(double i) {}
    void foo() {}

    void bar() {
        foo(1);
        foo(); // violation 'Method call to 'foo' needs "this.".'
    }
}

class NestedRechange2 {
    final String s = "";

    NestedRechange2() {
        String s = "t";
        s = s.substring(0);
    }

    private static class NestedStatic {
        static final String s = "";

        public void method() {
            s.substring(0);
        }
    }
}

class NestedFrames2 {
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
    }

    public int oneReturnInMethod3() {
        for (int b = 0; b < 10; b++) {
        }
        return b + b * b;
    }
    final NestedFrames NestedFrames = new NestedFrames();
}
