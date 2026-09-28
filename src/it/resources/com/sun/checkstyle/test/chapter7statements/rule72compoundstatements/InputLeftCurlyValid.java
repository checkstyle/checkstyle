package com.sun.checkstyle.test.chapter7statements.rule72compoundstatements;

// violation first line 'Header mismatch'

/**
 * Test input with opening brace at end of line correctly.
 */
public final class InputLeftCurlyValid {

    /**
     * Dummy method with opening brace at end of line.
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

}
