package com.sun.checkstyle.test.chapter4indentation.rule42wrappinglines;

// violation first line 'Header mismatch'

/**
 * Test input with operators wrapped after them.
 */
public final class InputOperatorWrapInvalid {

    /**
     * Dummy method with addition wrapped after the operator.
     *
     * @param first dummy first parameter.
     * @param second dummy second parameter.
     * @return combined value.
     */
    public int add(final int first, final int second) {
        final int sum = first + // violation ''\+' should be on a new line.'
                second;
        return sum;
    }

    /**
     * Dummy method with conjunction wrapped after the operator.
     *
     * @param first dummy first flag.
     * @param second dummy second flag.
     * @return combined flag.
     */
    public boolean both(final boolean first, final boolean second) {
        final boolean ok = first && // violation ''&&' should be on a new line.'
                second;
        return ok;
    }

}
