package com.sun.checkstyle.test.chapter7statements.rule78switchstatements;

/**
 * Test input for fall through with break statements.
 */
public final class InputFallThroughWithBreak {

    /**
     * Method with switch and missing breaks.
     *
     * @param value input value
     * @return result
     */
    public int method(final int value) {
        int result = 0;
        switch (value) {
            case 0:
                result = 1;
                break;
            case 1:
                result = 2;
                // violation below 'Fall through from previous branch'
            case 2:
                result = 0;
                break;
            default:
                result = 0;
        }
        return result;
    }
}
