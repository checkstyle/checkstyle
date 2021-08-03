/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public record InputRequireThisRecordDefault(int x, int y) {
    private static int i; // all fields must be static in a record definition

    public InputRequireThisRecordDefault {
        method1(); // ok
        method2(42); // ok
        method3(); // ok
        int z = x + y + 2; // ok
        System.out.println(y + x); // ok
    }

    InputRequireThisRecordDefault(int x) {
        this(x,42);
        x = x; // violation
    }

    public int getIPlusX() {
        return  i + x; // ok
    }

    public static void setI(int i) {
        InputRequireThisRecordDefault.i = i;
    }

    void method1() {
        i = 3 + y; // ok
        int w = this.x; // ok
    }

    void method2(int i) {
        i++;
        this.setI(i);
        method1(); // ok
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
        this.<String>method3();
    }
}
