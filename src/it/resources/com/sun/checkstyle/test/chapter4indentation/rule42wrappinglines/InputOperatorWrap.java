package com.sun.checkstyle.test.chapter4indentation.rule42wrappinglines;

// violation first line 'Header mismatch'

/**
 * Test input with operators wrapped correctly and incorrectly side by side.
 */
public final class InputOperatorWrap {

    /**
     * Dummy method with addition wrapped correctly and incorrectly.
     *
     * @param first dummy first parameter.
     * @param second dummy second parameter.
     * @return valid combined value.
     */
    public int add(final int first, final int second) {
        int sumWrong = first + // violation ''\+' should be on a new line.'
                second;
        int sumValid = first
                + second;
        return sumValid;
    }

    /**
     * Dummy method with conjunction wrapped correctly and incorrectly.
     *
     * @param first dummy first flag.
     * @param second dummy second flag.
     * @return valid combined flag.
     */
    public boolean both(final boolean first, final boolean second) {
        boolean andWrong = first && // violation ''&&' should be on a new line.'
                second;
        boolean andValid = first
                && second;
        return andValid;
    }

}
