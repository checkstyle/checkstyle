package com.sun.checkstyle.test.chapter7statements.rule75forstatements;

// violation first line 'Header mismatch'

/**
 * Test input with at most three variables in for clauses correctly.
 */
public final class InputDescendantTokenValid {

    /**
     * Dummy method with three variables declared in the for clauses.
     *
     * @param limit dummy iteration limit.
     * @return accumulated value.
     */
    public int sum(final int limit) {
        int total = 0;
        for (int a = 0, b = 0, c = 0; a < limit; a++, b++, c++) {
            total += a + b + c;
        }
        return total;
    }

    /**
     * Dummy method with three variables assigned in the for clauses.
     *
     * @param limit dummy iteration limit.
     * @return accumulated value.
     */
    public int reuse(final int limit) {
        int a;
        int b;
        int c;
        int total = 0;
        for (a = 0, b = 0, c = 0; a < limit; a++, b++, c++) {
            total += a + b + c;
        }
        return total;
    }

    /**
     * Dummy method with commas of a method call inside the for clause.
     *
     * @param limit dummy iteration limit.
     * @return accumulated value.
     */
    public int bounded(final int limit) {
        int total = 0;
        for (int a = Math.min(limit, 1), b = 0, c = 0; a < limit; a++) {
            total += a + b + c;
        }
        return total;
    }

}
