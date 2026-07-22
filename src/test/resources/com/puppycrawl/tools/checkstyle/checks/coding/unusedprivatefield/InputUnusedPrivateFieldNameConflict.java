/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldNameConflict {

    private int value; // violation, 'Unused private field'

    void methodWithSameNameLocal() {
        int value = 42;
    }

    private int total; // ok, as total is used

    void methodUsesFieldDirectly(int total) {
        this.total = total;
    }

    int getTotal() {
        return total;
    }

    static class NestedA {
        private int data; // violation, unused private field

        void doWork() {
            int data = 99;
        }
    }

    static class NestedB {
        private int data; // ok, as b is used

        int getData() {
            return data;
        }
    }

    private int index;

    void firstMethod() {
        System.out.println(index);
    }

    void secondMethod() {
        int index = 0;
        System.out.println(index);
    }

    private int i; // violation, unused private field

    void loopMethod() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
}
