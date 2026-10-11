package com.sun.checkstyle.test.chapter7statements.rule72compoundstatements;

// violation first line 'Header mismatch'

/**
 * Test input with opening brace on new line incorrectly.
 */
public final class InputLeftCurlyInvalid {

    /**
     * Dummy method with opening brace on new line.
     *
     * @param flag dummy flag.
     * @param one dummy first value.
     * @param two dummy second value.
     * @return chosen value.
     */
    public String pick(final boolean flag, final String one, final String two)
    { // violation ''{' at column 5 should be on the previous line.'
        return one;
    }

}
