package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

/*
 * Config = default
 */
public class InputRedundantThisEnumInnerClasses {
    int i;
    int j;

    void method1() {
        i = 3;
    }

    void method2(int j) {
        i++;
        this.j = j; // no violation
        method1();
        try {
            this.method1(); // violation
        } catch (RuntimeException e) {
            e.toString();
        }
        this.i--; // violation

        Integer.toString(10);
    }

    void method3() {
        i = 3;
    }

    void method4() {
        this.method3(); // violation
        method3();
    }
}

//  enum
enum MyEnum {
    A,
    B {
        void doSomething() {
            this.z = 1; // violation
        }
    };

    int z;

    private MyEnum() {
        z = 0;
    }
}
