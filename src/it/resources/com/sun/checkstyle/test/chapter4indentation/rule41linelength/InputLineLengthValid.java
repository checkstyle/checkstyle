package com.sun.checkstyle.test.chapter4indentation.rule41linelength;

// violation first line 'Header mismatch'

/**
 * Test input with all lines within 80 characters, this very line is exactly 80.
 */
public final class InputLineLengthValid {

    /**
     * Dummy method.
     *
     * @param value dummy parameter.
     * @return doubled value.
     */
    public int method(final int value) {
        return 2 * value;
    }

}
