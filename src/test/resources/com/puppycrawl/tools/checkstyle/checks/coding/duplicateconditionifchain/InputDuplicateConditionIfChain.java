/*
DuplicateConditionIfChain


*/

package com.puppycrawl.tools.checkstyle.checks.coding.duplicateconditionifchain;

public class InputDuplicateConditionIfChain {

    void testSimpleIfElseIf(int a) {
        if (a > 0) {
            System.out.println("positive");
        }
        // violation below 'Duplicate condition expression'
        else if (a > 0) {
            System.out.println("positive again");
        }
        else if (a == 0) {
            System.out.println("zero");
        }
    }

    void testMultipleConditionDuplication(int a, int b) {
        if (a > 0 && b < 10) {
            System.out.println("first");
        }
        else if (a < 0) {
            System.out.println("second");
        }
        // violation below 'Duplicate condition expression'
        else if (a > 0 && b < 10) {
            System.out.println("third");
        }
    }

    void testNoDuplication(int a) {
        if (a > 10) {
            System.out.println("gt 10");
        }
        else if (a > 5) {
            System.out.println("gt 5");
        }
        else if (a > 0) {
            System.out.println("gt 0");
        }
        else {
            System.out.println("else block");
        }
    }

    void testStandaloneIf(int a) {
        if (a > 0) {
            System.out.println("standalone");
        }
    }
}
