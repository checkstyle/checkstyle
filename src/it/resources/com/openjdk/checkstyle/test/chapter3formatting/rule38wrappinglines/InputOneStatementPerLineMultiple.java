package com.openjdk.checkstyle.test.chapter3formatting.rule38wrappinglines;

/**
 * Test input for multiple statements per line.
 */
public final class InputOneStatementPerLineMultiple {
    /** Dummy variable. */
    private int one = 0;

    /** Dummy variable. */
    private int two = 0;

    /**
     * Method with multiple violations.
     */
    public void method() {
        int num = 0;
        int count = 1; count++; // violation 'Only one statement'
        num++; count++; // violation 'Only one statement per line allowed.'
    }

    /**
     * Method with while loop violation.
     */
    public void whileMethod() {
        while (one < 2) {
            one++; two--; // violation 'Only one statement per line allowed.'
        }
    }

    /**
     * Method with if statement violation.
     */
    public void ifMethod() {
        if (one == 1) {
            one++; two++; // violation 'Only one statement per line allowed.'
        }
    }

    /**
     * Method with method call violations.
     */
    public void callMethod() {
        toString(); toString(); // violation 'Only one statement'
    }
}
