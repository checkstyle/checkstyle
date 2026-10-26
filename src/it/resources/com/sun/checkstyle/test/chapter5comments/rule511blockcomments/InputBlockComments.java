package com.sun.checkstyle.test.chapter5comments.rule511blockcomments;

// violation first line 'Header mismatch'

/**
 * Test input for block comments check in Sun style.
 */
public final class InputBlockComments {

    /**
     * Dummy method demonstrating valid and invalid block comments.
     *
     * @param flag dummy flag.
     * @return accumulated value.
     */
    public int testBlockComments(final boolean flag) {
        /*
         * Valid block comment at method level indent.
         */
        int count = 0;

        if (flag) {
            /*
             * Valid block comment inside if block.
             */
            count = 1;
        }

        // violation below 'Block comment has incorrect indentation.*'
    /*
     * Misaligned block comment with less indent.
     */
        count = 1;

        // violation below 'Block comment has incorrect indentation.*'
            /*
             * Misaligned block comment with extra indent.
             */
        count = 1;

        return count;
    }

}
