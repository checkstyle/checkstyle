package com.openjdk.checkstyle.test.chapter3formatting.rule38wrappinglines;

/**
 * Test input for one statement per line with default violations.
 */
public final class InputOneStatementPerLineDefault {

    /** Dummy variable. */
    private int one = 0;

    /** Dummy variable. */
    private int two = 0;

    /**
     * Simple legal method.
     */
    public void doLegal() {
        one = 1;
        two = 2;
    }

    /**
     * Simplest form of illegal layouts.
     */
    public void doIllegal() {
        one = 1;
        two = 2; // violation 'Only one statement per line allowed.'
        if (one == 1) {
            one++;
            two++; // violation 'Only one statement per line allowed.'
        }
        doLegal();
        doLegal(); // violation 'Only one statement'
    }

    /**
     * Two statements distributed over two lines.
     */
    public void doIllegalTwo() {
        one = 1;
        two = 2; // violation 'Only one statement per line allowed.'
    }
}
