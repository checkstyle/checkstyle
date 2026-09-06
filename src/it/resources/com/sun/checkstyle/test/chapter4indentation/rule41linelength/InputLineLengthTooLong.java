package com.sun.checkstyle.test.chapter4indentation.rule41linelength;

// violation first line 'Header mismatch'

/**
 * Test input with lines longer than 80 characters.
 */
public final class InputLineLengthTooLong {

    /** Dummy constant. */
    private static final String LONG_CONSTANT = "This is a very long string literal for testing.";
    // violation above 'Line is longer than 80 characters (found 98).'

    /**
     * Dummy method.
     *
     * @param firstParameter dummy first parameter.
     * @param secondParameter dummy second parameter.
     * @return combined value.
     */
    public int methodWithVeryLongName(final int firstParameter, final int secondParameter) {
        // violation above 'Line is longer than 80 characters (found 92).'
        final String veryLongResultMessage = "This is a very long message for testing purposes.";
        // violation above 'Line is longer than 80 characters (found 97).'
        return firstParameter + secondParameter;
    }

}
