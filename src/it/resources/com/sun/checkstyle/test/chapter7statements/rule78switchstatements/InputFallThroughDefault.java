package com.sun.checkstyle.test.chapter7statements.rule78switchstatements;

/**
 * Test input for fall through with default violations.
 */
public final class InputFallThroughDefault {

    /**
     * Method with switch fall through violations.
     *
     * @param value input value
     * @return result
     */
    public int method(final int value) {
        int result = 0;
        switch (value) {
            case 0:
                result = 1;
                // violation below 'Fall through from previous branch'
            case 1:
                result = 2;
                break;
            default:
                result = 0;
        }
        return result;
    }
}
