package com.sun.checkstyle.test.chapter4indentation.rule42wrappinglines;

// violation first line 'Header mismatch'

/**
 * Test input where operators wrap before them, and this line is exactly eighty.
 */
public final class InputOperatorWrapValid {

    /**
     * Dummy method with addition wrapped before the operator.
     *
     * @param first dummy first parameter.
     * @param second dummy second parameter.
     * @return combined value.
     */
    public int add(final int first, final int second) {
        final int sum = first
                + second;
        return sum;
    }

    /**
     * Dummy method with conjunction wrapped before the operator.
     *
     * @param first dummy first flag.
     * @param second dummy second flag.
     * @return combined flag.
     */
    public boolean both(final boolean first, final boolean second) {
        final boolean result = first
                && second;
        return result;
    }

}
