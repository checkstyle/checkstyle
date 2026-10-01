package com.sun.checkstyle.test.chapter7statements.rule75forstatements;

// violation first line 'Header mismatch'

/**
 * Test input with more than three variables in for clauses incorrectly.
 */
public final class InputDescendantTokenInvalid {

    /**
     * Dummy method with four variables declared in the initialization.
     *
     * @param limit dummy iteration limit.
     * @return accumulated value.
     */
    public int sum(final int limit) {
        int total = 0;
        // violation below 'Avoid using more than three variables'
        for (int a = 0, b = 0, c = 0, d = 0; a < limit; a++) {
            total += a + b + c + d;
        }
        return total;
    }

    /**
     * Dummy method with four variables assigned in the initialization.
     *
     * @param limit dummy iteration limit.
     * @return accumulated value.
     */
    public int reuse(final int limit) {
        int a;
        int b;
        int c;
        int d;
        int total = 0;
        // violation below 'Avoid using more than three variables'
        for (a = 0, b = 0, c = 0, d = 0; a < limit; a++) {
            total += a + b + c + d;
        }
        return total;
    }

    /**
     * Dummy method with four variables in the update clause.
     *
     * @param limit dummy iteration limit.
     * @return accumulated value.
     */
    public int step(final int limit) {
        int b = 0;
        int c = 0;
        int d = 0;
        int total = 0;
        // violation below 'Avoid using more than three variables'
        for (int a = 0; a < limit; a++, b++, c++, d++) {
            total += a + b + c + d;
        }
        return total;
    }

}
