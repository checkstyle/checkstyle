package com.sun.checkstyle.test.chapter8whitespace.rule82blankspaces;

// violation first line 'Header mismatch'

/**
 * Test input with whitespace before tokens, valid close to invalid.
 * Commas, semicolons, post-increment, post-decrement and labels
 * take no space before them.
 */
public final class InputNoWhitespaceBeforeInvalid {

    /**
     * Dummy method with comma spacing.
     *
     * @param one dummy first value.
     * @param two dummy second value.
     * @return combined value.
     */
    public String combine(final String one, final String two) {
        String result = combine(one, two);
        result = combine(one , two);
        // violation above '',' is preceded with whitespace.'
        return result;
    }

    /**
     * Dummy method with semicolon spacing.
     *
     * @return sum of indices.
     */
    public int sum() {
        int total = 0;
        for (int index = 0; index < 2; index++) {
            total = total + index;
        }
        int other = 1 ;
        // violation above '';' is preceded with whitespace.'
        other = 2;
        return total + other;
    }

    /**
     * Dummy method with post-increment spacing.
     *
     * @param start dummy start value.
     * @return incremented value.
     */
    public int rise(final int start) {
        int count = start;
        count++;
        count ++;
        // violation above ''\+\+' is preceded with whitespace.'
        return count;
    }

    /**
     * Dummy method with post-decrement spacing.
     *
     * @param start dummy start value.
     * @return decremented value.
     */
    public int fall(final int start) {
        int count = start;
        count--;
        count --;
        // violation above ''--' is preceded with whitespace.'
        return count;
    }

}
