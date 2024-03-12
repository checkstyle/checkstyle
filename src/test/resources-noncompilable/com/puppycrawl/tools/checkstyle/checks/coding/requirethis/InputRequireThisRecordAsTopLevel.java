/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public record InputRequireThisRecordAsTopLevel(int x, int y) {
    private static int i; // all fields must be static in a record definition

    public InputRequireThisRecordAsTopLevel {
        method1(); // violation 'Method call to 'method1' needs "this.".'
        method2(42); // violation 'Method call to 'method2' needs "this.".'
        method3(); // violation 'Method call to 'method3' needs "this.".'
        int z = x + y + 2; // ok, 'this' cannot be used in COMPACT_CTOR_DEF
        System.out.println(y + x); // ok, 'this' cannot be used in COMPACT_CTOR_DEF
    }

    InputRequireThisRecordAsTopLevel(int x) {
        this(x,42);
        method1(); // violation 'Method call to 'method1' needs "this.".'
    }

    public int getIPlusX() {
        return  i + x; // violation 'Reference to instance variable 'x' needs "this.".'
    }

    public static void setI(int i) {
        InputRequireThisRecordAsTopLevel.i = i;
    }

    void method1() {
        i = 3 + y; // violation 'Reference to instance variable 'y' needs "this.".'
        int w = this.x;
    }

    void method2(int i) {
        i++;
        this.setI(i);
        method1(); // violation 'Method call to 'method1' needs "this.".'
        try {
            this.method1();
        } catch (RuntimeException e) {
            e.toString();
        }
        this.setI(this.getIPlusX() - 1);

        Integer.toString(10);
    }

    <T> void method3() {
        setI(3);
    }

    void method4() {
        this.<String>method3();
    }
}
