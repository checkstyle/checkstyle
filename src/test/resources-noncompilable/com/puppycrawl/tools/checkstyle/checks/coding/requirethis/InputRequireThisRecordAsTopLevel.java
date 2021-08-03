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
        method1(); // violation
        method2(42); // violation
        method3(); // violation
        int z = x + y + 2; // ok, 'this' cannot be used in COMPACT_CTOR_DEF
        System.out.println(y + x); // ok, 'this' cannot be used in COMPACT_CTOR_DEF
    }

    InputRequireThisRecordAsTopLevel(int x) {
        this(x,42);
        method1(); // violation
    }

    public int getIPlusX() {
        return  i + x; // violation
    }

    public static void setI(int i) {
        InputRequireThisRecordAsTopLevel.i = i;
    }

    void method1() {
        i = 3 + y; // violation
        int w = this.x; // ok
    }

    void method2(int i) {
        i++;
        this.setI(i);
        method1(); // violation
        try {
            this.method1(); // ok
        } catch (RuntimeException e) {
            e.toString();
        }
        this.setI(this.getIPlusX() - 1);

        Integer.toString(10);
    }

    <T> void method3() {
        setI(3); // ok
    }

    void method4() {
        this.<String>method3(); // ok
    }
}
