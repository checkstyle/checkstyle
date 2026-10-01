package com.sun.checkstyle.test.chapter7statements.rule75forstatements;

// violation first line 'Header mismatch'

/**
 * Test input with for statement opening brace at end of line correctly.
 */
public final class InputLeftCurlyValid {

    /**
     * Dummy method with opening brace at end of the for statement line.
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

}
