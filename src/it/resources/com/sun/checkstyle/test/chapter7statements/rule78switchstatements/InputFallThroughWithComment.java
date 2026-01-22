package com.sun.checkstyle.test.chapter7statements.rule78switchstatements;

/**
 * Test input for fall through with relief comment.
 */
public final class InputFallThroughWithComment {

    /**
     * Method with switch and fall through comments.
     *
     * @param value input value
     * @return result
     */
    public int method(final int value) {
        int result = 0;
        switch (value) {
            case 0:
                result = 1;
                // fall through
            case 1:
                result = 2;
                /* falls through */
            case 2:
                result = 0;
                break;
            default:
                result = 0;
        }
        return result;
    }
}
