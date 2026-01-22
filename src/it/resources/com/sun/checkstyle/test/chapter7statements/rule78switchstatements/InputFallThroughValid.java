package com.sun.checkstyle.test.chapter7statements.rule78switchstatements;

/**
 * Test input for valid switch statements (no fall through violations).
 */
public final class InputFallThroughValid {

    /**
     * Method with properly terminated switch cases.
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
                break;
            case 2:
                result = 0;
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }

    /**
     * Method with empty cases (allowed to fall through).
     *
     * @param value input value
     * @return result
     */
    public int emptyCase(final int value) {
        int result = 0;
        switch (value) {
            case 0:
            case 1:
            case 2:
                result = 1;
                break;
            default:
                result = 0;
        }
        return result;
    }
}
