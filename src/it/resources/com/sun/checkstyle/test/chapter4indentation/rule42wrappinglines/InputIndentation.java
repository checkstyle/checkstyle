package com.sun.checkstyle.test.chapter4indentation.rule42wrappinglines;

// violation first line 'Header mismatch'

/**
 * Test input with continuation indent valid and invalid side by side.
 */
public final class InputIndentation {

    /**
     * Dummy method with continuation correctly and incorrectly indented.
     *
     * @param first dummy first parameter.
     * @param second dummy second parameter.
     * @return valid combined value.
     */
    public String join(final String first, final String second) {
        String aligned = joinCall(first,
                second);
        String shortIndent = joinCall(first,
        second);
        // violation above 'level 8, expected level should be 12'
        return aligned + shortIndent;
    }

    /**
     * Helper to wrap arguments.
     *
     * @param first dummy first parameter.
     * @param second dummy second parameter.
     * @return combined value.
     */
    public String joinCall(final String first, final String second) {
        return first + second;
    }

}
