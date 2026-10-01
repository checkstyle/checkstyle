package com.sun.checkstyle.test.chapter7statements.rule75forstatements;

// violation first line 'Header mismatch'

/**
 * Test input with for statement opening brace on new line incorrectly.
 */
public final class InputLeftCurlyInvalid {

    /**
     * Dummy method with opening brace on the line after the for statement.
     *
     * @param limit dummy iteration limit.
     * @return accumulated value.
     */
    public int count(final int limit) {
        int total = 0;
        for (int index = 0; index < limit; index++)
        { // violation ''{' at column 9 should be on the previous line.'
            total += index;
        }
        return total;
    }

}
