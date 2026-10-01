package com.sun.checkstyle.test.chapter7statements.rule75forstatements;

// violation first line 'Header mismatch'

/**
 * Test input with for statements using braces correctly.
 */
public final class InputNeedBracesValid {

    /**
     * Dummy method with a braced for statement.
     *
     * @param limit dummy iteration limit.
     * @return accumulated value.
     */
    public int count(final int limit) {
        int total = 0;
        for (int index = 0; index < limit; index++) {
            total += index;
        }
        return total;
    }

    /**
     * Dummy method with a braced for statement using the comma operator.
     *
     * @param limit dummy iteration limit.
     * @return accumulated value.
     */
    public int combine(final int limit) {
        int total = 0;
        for (int first = 0, second = 1; first < limit; first++) {
            total += first + second;
        }
        return total;
    }

}
