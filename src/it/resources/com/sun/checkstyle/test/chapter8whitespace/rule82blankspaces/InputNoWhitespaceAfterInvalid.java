package com.sun.checkstyle.test.chapter8whitespace.rule82blankspaces;

// violation first line 'Header mismatch'

/**
 * Test input with whitespace after tokens, valid close to invalid.
 * Increment, decrement, unary minus, logical complement, bitwise complement
 * and dot take no space after them.
 */
public final class InputNoWhitespaceAfterInvalid {

    /**
     * Dummy method with increment spacing.
     *
     * @param start dummy start value.
     * @return incremented value.
     */
    public int rise(final int start) {
        int count = start;
        count++;
        ++count;
        ++ count; // violation ''++' is followed by whitespace.'
        return count;
    }

    /**
     * Dummy method with decrement spacing.
     *
     * @param start dummy start value.
     * @return decremented value.
     */
    public int fall(final int start) {
        int count = start;
        count--;
        --count;
        -- count; // violation ''--' is followed by whitespace.'
        return count;
    }

    /**
     * Dummy method with unary minus spacing.
     *
     * @param start dummy start value.
     * @return negated value.
     */
    public int negate(final int start) {
        int value = -start;
        value = - start; // violation ''-' is followed by whitespace.'
        return value;
    }

    /**
     * Dummy method with logical complement spacing.
     *
     * @param flag dummy flag.
     * @return flipped flag.
     */
    public boolean flip(final boolean flag) {
        boolean result = !flag;
        result = ! flag; // violation ''!' is followed by whitespace.'
        return result;
    }

    /**
     * Dummy method with bitwise complement spacing.
     *
     * @param mask dummy mask.
     * @return inverted mask.
     */
    public int invert(final int mask) {
        int value = ~mask;
        value = ~ mask; // violation ''~' is followed by whitespace.'
        return value;
    }

    /**
     * Dummy method with dot spacing.
     *
     * @param one dummy first value.
     * @param two dummy second value.
     * @return combined value.
     */
    public String join(final String one, final String two) {
        String result = one.concat(two);
        result = one. concat(two);
        // violation above ''.' is followed by whitespace.'
        return result;
    }

}
