package com.sun.checkstyle.test.chapter5comments.rule512singlelinecomments;

// violation first line 'Header mismatch'

/**
 * Test input for single-line comments check in Sun style.
 */
public final class InputSingleLineComments {

    /**
     * Dummy method demonstrating valid and invalid single-line comments.
     *
     * @param flag dummy flag.
     * @return accumulated value.
     */
    public int testSingleLineComments(final boolean flag) {
        // Valid single-line comment at method level indent.
        int count = 0;

        if (flag) {
            // Valid single-line comment inside if block.
            count = 1;
        }

        // violation below 'Comment has incorrect indentation.*'
    // Misaligned comment with less indent.
        count = 1;

        // violation below 'Comment has incorrect indentation.*'
            // Misaligned comment with extra indent.
        count = 1;

        return count;
    }

}
