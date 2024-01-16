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
        method1();
        method2(42);
        method3();
        int z = x + y + 2;
        System.out.println(y + x);
    }

    InputRequireThisRecordDefault(int x) {
        this(x,42);
        x = x; // violation 'Reference to instance variable 'x' needs "this.".'
    }

    public int getIPlusX() {
        return  i + x;
    }

    public static void setI(int i) {
        InputRequireThisRecordDefault.i = i;
    }

    void method1() {
        i = 3 + y;
        int w = this.x;
    }

    void method2(int i) {
        i++;
        this.setI(i);
        method1();
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
