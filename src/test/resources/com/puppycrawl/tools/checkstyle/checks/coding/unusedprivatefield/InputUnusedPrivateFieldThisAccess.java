/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldThisAccess {

    private int allLayers; // ok, private field is used

    void method1() {
        this.allLayers = 10;
    }

    private int servicesCatalog; // ok, private field is used

    int method2() {
        return this.servicesCatalog;
    }

    private int value; // violation 'Unused private field'

    void method3(int value) {
        System.out.println(value);
    }

    private int count; // violation 'Unused private field'

    void method4() {
        int count = 5;
        System.out.println(count);
    }

    private int total; // ok, private field is used

    void method5(int total) {
        this.total = total;
    }

    private int flag; // violation 'Unused private field'

    {
        int flag = 1;
        System.out.println(flag);
    }

    static class Nested {
        private int data; // violation 'Unused private field'

        void work() {
            int data = 99;
            System.out.println(data);
        }
    }

    abstract static class WithAbstract {
        private int size; // violation 'Unused private field'

        abstract void process(int size);
    }

    private int source; // ok, private field is used

    InputUnusedPrivateFieldThisAccess(int source) {
        this.source = source;
    }

    int getSource() {
        return source;
    }

    private int score; // ok, private field is used

    void method6() {
        InputUnusedPrivateFieldThisAccess obj =
            new InputUnusedPrivateFieldThisAccess(1);
        System.out.println(obj.score);
    }
    private static class TimedElement {
        private long startTime; // ok, private field is used
    }

    private TimedElement element = new TimedElement();

    void start() {
        element.startTime = System.currentTimeMillis();
    }

    void finish() {
        long total = System.currentTimeMillis() - element.startTime;
        System.out.println(total);
    }
}
