package com.sun.checkstyle.test.chapter7statements.rule74ifstatements;

// violation first line 'Header mismatch'

/**
 * Test input with if statements using braces correctly.
 */
public final class InputNeedBracesValid {

    /**
     * Dummy method with braced if else statements.
     *
     * @param flag dummy flag.
     * @param one dummy first value.
     * @param two dummy second value.
     * @return chosen value.
     */
    public String pick(final boolean flag, final String one, final String two) {
        if (flag) {
            return one;
        } else {
            return two;
        }
    }

    /**
     * Dummy method with braced else if statements.
     *
     * @param flag dummy first flag.
     * @param other dummy second flag.
     * @return chosen value.
     */
    public String select(final boolean flag, final boolean other) {
        if (flag) {
            return "first";
        } else if (other) {
            return "second";
        } else {
            return "third";
        }
    }

}
