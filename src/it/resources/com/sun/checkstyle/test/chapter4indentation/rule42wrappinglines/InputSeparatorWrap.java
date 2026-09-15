package com.sun.checkstyle.test.chapter4indentation.rule42wrappinglines;

// violation first line 'Header mismatch'

/**
 * Test input with commas wrapped correctly and incorrectly side by side.
 */
public final class InputSeparatorWrap {

    /**
     * Dummy method with comma wrapped correctly and incorrectly.
     *
     * @param first dummy first parameter.
     * @param second dummy second parameter.
     * @return valid combined value.
     */
    public String join(final String first, final String second) {
        String commaValid = joinCall(first,
                second);
        // 2 violations 4 lines below:
        //   '',' is preceded with whitespace.'
        //   '',' should be on the previous line.'
        String commaWrong = joinCall(first
                , second);
        return commaValid + commaWrong;
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
