package com.openjdk.checkstyle.test.chapter3formatting.rule38wrappinglines;

/**
 * Test input for valid one statement per line.
 */
public final class InputOneStatementPerLineValid {

    /** Dummy variable. */
    private int one = 0;

    /** Dummy variable. */
    private int two = 0;

    /**
     * Method with proper one statement per line.
     */
    public void method() {
        int num = 0;
        int count = 1;
        num++;
        count++;
    }

    /**
     * Legal method with separate lines.
     */
    public void doLegal() {
        one = 1;
        two = 2;
    }

    /**
     * String with format inside is legal.
     */
    public void doLegalString() {
        one = 1;
        two = 2;
        System.identityHashCode("one = 1; two = 2");
    }

    /**
     * Break and continue statements.
     */
    public void loopMethod() {
        int sum = 0;
        for (int i = 0; i < 2; i++) {
            sum += i;
            if (sum > 1) {
                break;
            }
        }

        while (one < 2) {
            one++;
        }

        do {
            two++;
        }
        while (two < 2);
    }
}
