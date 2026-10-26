package com.sun.checkstyle.test.chapter5comments.rule513trailingcomments;

// violation first line 'Header mismatch'

/**
 * Test input for trailing comments check in Sun style.
 */
public final class InputTrailingComments {

    /**
     * Dummy method demonstrating valid and trailing comments.
     *
     * @param flag dummy flag.
     * @return accumulated value.
     */
    public int testTrailingComments(final boolean flag) {
        // Valid comment alone on its own line.
        int count = 0;

        /* Valid block comment alone on its own line. */
        if (flag) {
            count = 1;
        }

        // violation below 'Don't use trailing comments.'
        count = 1; // Trailing single-line comment.

        // violation below 'Don't use trailing comments.'
        count = 1; /* Trailing block comment. */

        return count;
    }

}
