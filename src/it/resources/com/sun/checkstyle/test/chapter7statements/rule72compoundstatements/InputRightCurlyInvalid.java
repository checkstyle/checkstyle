package com.sun.checkstyle.test.chapter7statements.rule72compoundstatements;

// violation first line 'Header mismatch'

/**
 * Test input with closing brace alone on line incorrectly.
 */
public final class InputRightCurlyInvalid {

    /**
     * Dummy method with closing brace alone on line.
     *
     * @param flag dummy flag.
     * @param one dummy first value.
     * @param two dummy second value.
     * @return chosen value.
     */
    public String pick(final boolean flag, final String one, final String two) {
        if (flag) {
            return one;
        } // violation ''}' at column 9 should be on the same line.'
        else {
            return two;
        }
    }

}
