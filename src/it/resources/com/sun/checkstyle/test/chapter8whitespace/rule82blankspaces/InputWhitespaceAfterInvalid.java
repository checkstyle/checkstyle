package com.sun.checkstyle.test.chapter8whitespace.rule82blankspaces;

// violation first line 'Header mismatch'

/**
 * Test input with missing blank spaces, valid close to invalid.
 * Commas in argument lists, semicolons in for statements and
 * typecasts require a blank space after them.
 */
public final class InputWhitespaceAfterInvalid {

    /**
     * Dummy method with comma spacing in argument lists.
     *
     * @param one dummy first value.
     * @param two dummy second value.
     */
    public void show(final String one, final String two) {
        combine(one, two);
        combine(one,two); // violation '',' is not followed by whitespace.'
    }

    /**
     * Dummy method with for statement semicolon spacing.
     *
     * @return sum of indices.
     */
    public int sum() {
        int total = 0;
        for (int index = 0; index < 2; index++) {
            total = total + index;
        }
        // 2 violations 3 lines below:
        //   '';' is not followed by whitespace.'
        //   '';' is not followed by whitespace.'
        for (int index = 0;index < 2;index++) {
            total = total + index;
        }
        return total;
    }

    /**
     * Dummy method with typecast spacing.
     *
     * @param value dummy value.
     * @return widened value.
     */
    public long widen(final int value) {
        long result = (long) value;
        result = (long)value;
        // violation above ''typecast' is not followed by whitespace.'
        return result;
    }

    /**
     * Dummy helper combining two values.
     *
     * @param one dummy first value.
     * @param two dummy second value.
     * @return combined value.
     */
    public String combine(final String one, final String two) {
        return one.concat(two);
    }

}
