package com.sun.checkstyle.test.chapter5comments.rule514endoflinecomments;

// violation first line 'Header mismatch'

/**
 * Test input for end-of-line comments check in Sun style.
 */
public final class InputEndOfLineComments {

    /**
     * Dummy method demonstrating valid and invalid end-of-line comments.
     *
     * @param flag dummy flag.
     * @return accumulated value.
     */
    public int testEndOfLineComments(final boolean flag) {
        // Valid end-of-line comment at method level indent.
        int count = 0;

        if (flag) {
            // Valid comment inside if block.
            count = 1;
        }

        // Commented-out block of code.
        // if (flag) {
        //     count = 0;
        // }

        // violation below 'Comment has incorrect indentation.*'
    // Misaligned comment with less indent.
        count = 1;

        // violation below 'Comment has incorrect indentation.*'
            // Misaligned comment with extra indent.
        count = 1;

        return count;
    }

}
