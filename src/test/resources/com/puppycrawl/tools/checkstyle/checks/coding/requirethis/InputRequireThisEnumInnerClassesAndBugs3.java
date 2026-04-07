/*
RequireThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisEnumInnerClassesAndBugs3 {
    int i;
    void method1() {
        i = 3; // violation 'Reference to instance variable 'i' needs "this.".'
    }

    void method2(int i) {
        i++;
        this.i = i;
        method1();
        try {
            this.method1();
        }
        catch (RuntimeException e) {
            e.toString();
        }
        this.i--;

        Integer.toString(10);
    }

    <T> void method3()
    {
        i = 3; // violation 'Reference to instance variable 'i' needs "this.".'
    }

    void method4() {
        this.<String>method3();
        this.<I>method3();
    }
    int I = 0;
    private class I {}
}

//  enum
enum MyEnum3
{
    A,
    B
    {
        void doSomething()
        {
            z = 1; // violation 'Reference to instance variable 'z' needs "this.".'
        }
    };

    int z;
    private MyEnum3()
    {
        z = 0; // violation 'Reference to instance variable 'z' needs "this.".'
    }
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
        return a + a * a; // 3 violations
    }

    public int oneReturnInMethod3() {
        for (int b = 0; b < 10; b++) {
        }
        return b + b * b; // 3 violations
    }
    final NestedFrames NestedFrames = new NestedFrames();
}
