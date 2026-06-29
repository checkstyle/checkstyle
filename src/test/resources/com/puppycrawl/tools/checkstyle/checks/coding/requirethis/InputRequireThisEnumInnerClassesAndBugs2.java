/*
RequireThis
checkFields = false
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisEnumInnerClassesAndBugs2 {
    int i;
    void method1() {
        i = 3;
    }

    void method2(int i) {
        i++;
        this.i = i;
        method1(); // violation 'Method call to 'method1' needs "this.".'
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
        i = 3;
    }

    void method4() {
        this.<String>method3();
        this.<I>method3();
    }
    int I = 0;
    private class I {}
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
