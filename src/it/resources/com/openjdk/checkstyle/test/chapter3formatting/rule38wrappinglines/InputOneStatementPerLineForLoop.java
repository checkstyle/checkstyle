package com.openjdk.checkstyle.test.chapter3formatting.rule38wrappinglines;

/**
 * Test input for one statement per line in for loop.
 */
public final class InputOneStatementPerLineForLoop {

    /**
     * Method with for loop - multiple statements in header are allowed.
     */
    public void method() {
        int sum = 0;
        for (int i = 0, j = 0; i < 2; i++, j++) {
            sum += i + j;
        }
    }

    /**
     * Multiline for loop is legal.
     */
    public void multilineFor() {
        int sum = 0;
        for (int i = 0,
             j = 1;
             i < 2;
             i++, j--) {
            sum += i;
        }
    }

    /**
     * Forever loop is legal.
     */
    public void foreverLoop() {
        int count = 0;
        for (;;) {
            count++;
            if (count > 1) {
                break;
            }
        }
    }

    /**
     * One statement inside for block is legal.
     */
    public void singleStatementFor() {
        int one = 0;
        for (int i = 0; i < 2; i++) {
            one = i;
        }
    }
}
