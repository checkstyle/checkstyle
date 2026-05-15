/*
UnusedPrivateField

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldNameConflict {

    private int value; // violation, unused private field

    void methodWithSameNameLocal() {
        int value = 42; // local – does NOT count as a use of the field
    }

    private int total; // ok, used via this.total

    void methodUsesFieldDirectly(int total) {
        this.total = total;
    }

    int getTotal() {
        return total; // read of the field
    }

    static class NestedA {
        private int data; // violation, unused private field

        void doWork() {
            int data = 99; // local shadows field; field still unused
        }
    }

    static class NestedB {
        private int data; // ok, used below

        int getData() {
            return data; // uses NestedB.data
        }
    }

    private int index; // ok, read in firstMethod

    void firstMethod() {
        System.out.println(index); // uses the field
    }

    void secondMethod() {
        int index = 0; // local only; field already used in firstMethod → no violation
        System.out.println(index);
    }

    private int i; // violation, unused private field

    void loopMethod() {
        for (int i = 0; i < 10; i++) { // loop var, not the field
            System.out.println(i);
        }
    }
}
