package com.sun.checkstyle.test.chapter8whitespace.rule82blankspaces;

// violation first line 'Header mismatch'

/**
 * Test input with method name and parenthesis spacing, valid close to invalid.
 * Method definitions, calls and constructor invocations take no space.
 */
public final class InputMethodParamPadInvalid {

    /**
     * Dummy method with method call spacing.
     *
     * @param one dummy first value.
     * @param two dummy second value.
     * @return combined value.
     */
    public String pick(final String one, final String two) {
        String result = combine(one, two);
        result = combine (one, two);
        // violation above ''(' is preceded with whitespace.'
        result = new String("inner");
        result = new String ("inner");
        // violation above ''(' is preceded with whitespace.'
        return result;
    }

    /**
     * Dummy helper with correct spacing.
     *
     * @param one dummy first value.
     * @param two dummy second value.
     * @return combined value.
     */
    public String combine(final String one, final String two) {
        return one.concat(two);
    }

    /**
     * Dummy method with incorrect definition spacing.
     *
     * @param one dummy first value.
     * @return first value.
     */
    public String fetch (final String one) {
        // violation above ''(' is preceded with whitespace.'
        return one;
    }

}
