package com.sun.checkstyle.test.chapter8whitespace.rule82blankspaces;

// violation first line 'Header mismatch'

/**
 * Test input with whitespace around tokens, valid close to invalid.
 * Keywords and operators require whitespace around them.
 */
public final class InputWhitespaceAroundInvalid {

    /**
     * Dummy method with if keyword spacing.
     *
     * @param flag dummy flag value.
     * @return result value.
     */
    public int check(final boolean flag) {
        int result = 0;
        if (flag) {
            result = 1;
        }
        if(flag) {
            // violation above ''if' is not followed by whitespace.'
            result = 2;
        }
        return result;
    }

    /**
     * Dummy method with plus operator spacing.
     *
     * @param total dummy total value.
     * @return updated total.
     */
    public int increment(final int total) {
        int result = total + 1;
        result = total+ 1;
        // violation above ''\+' is not preceded with whitespace.'
        result = total +1;
        // violation above ''\+' is not followed by whitespace.'
        return result;
    }

    /**
     * Dummy method with assign operator spacing.
     *
     * @param value dummy value.
     * @return assigned value.
     */
    public int assign(final int value) {
        int result = 0;
        result = value;
        result =value;
        // violation above ''=' is not followed by whitespace.'
        result =2;
        // violation above ''=' is not followed by whitespace.'
        return result;
    }

}
