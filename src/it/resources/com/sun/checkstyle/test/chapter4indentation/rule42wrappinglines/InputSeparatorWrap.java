package com.sun.checkstyle.test.chapter4indentation.rule42wrappinglines;

// violation first line 'Header mismatch'

/**
 * Test input with separators wrapped correctly and incorrectly side by side.
 */
public final class InputSeparatorWrap {

    /**
     * Dummy method with dot wrapped correctly and incorrectly.
     *
     * @param first dummy first part.
     * @param second dummy second part.
     * @return combined value.
     */
    public String join(final String first, final String second) {
        StringBuilder builder = new StringBuilder();
        String dotWrong = builder
        // violation below ''.' should be on the previous line.'
                .append(first).toString();
        String dotValid = builder.
                append(second).toString();
        return dotWrong + dotValid;
    }

}
