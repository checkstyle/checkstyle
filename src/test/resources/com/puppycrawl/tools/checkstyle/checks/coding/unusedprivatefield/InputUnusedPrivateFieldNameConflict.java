/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldNames = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldNameConflict {

    private int value; // violation 'Unused private field'

    void methodWithSameNameLocal() {
        int value = 42;
    }

    private int total; // ok, private field is used

    void methodUsesFieldDirectly(int total) {
        this.total = total;
    }

    int getTotal() {
        return total;
    }

    static class NestedA {
        private int data; // violation 'Unused private field'

        void doWork() {
            int data = 99;
        }
    }

    static class NestedB {
        private int data; // ok, private field is used

        int getData() {
            return data;
        }
    }

    private int index; // ok, private field is used

    void firstMethod() {
        System.out.println(index);
    }

    void secondMethod() {
        int index = 0;
        System.out.println(index);
    }

    private int i; // violation 'Unused private field'

    void loopMethod() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
}
