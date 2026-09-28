package com.sun.checkstyle.test.chapter7statements.rule72compoundstatements;

// violation first line 'Header mismatch'

/**
 * Test input with closing brace placement, valid close to invalid.
 * Class and method closing braces alone on line are valid.
 */
public final class InputRightCurlyInvalid {

    /**
     * Dummy method with if else brace placement.
     *
     * @param flag dummy flag.
     * @param one dummy first value.
     * @param two dummy second value.
     * @return chosen value.
     */
    public String pick(final boolean flag, final String one, final String two) {
        String result = one;
        if (flag) {
            result = one;
        } else {
            result = two;
        }
        if (flag) {
            result = one;
        } // violation ''}' at column 9 should be on the same line.'
        else {
            result = two;
        }
        return result;
    }

    /**
     * Dummy method with else if brace placement.
     *
     * @param flag dummy first flag.
     * @param other dummy second flag.
     * @return chosen value.
     */
    public String select(final boolean flag, final boolean other) {
        String result = "first";
        if (flag) {
            result = "first";
        } else if (other) {
            result = "second";
        } else {
            result = "third";
        }
        if (flag) {
            result = "first";
        } // violation ''}' at column 9 should be on the same line.'
        else if (other) {
            result = "second";
        } else {
            result = "third";
        }
        return result;
    }

    /**
     * Dummy method with try catch brace placement.
     *
     * @param one dummy first value.
     * @param two dummy second value.
     * @return chosen value.
     */
    public String fetch(final String one, final String two) {
        String result = one;
        try {
            result = one;
        } catch (IllegalStateException ex) {
            result = two;
        }
        try {
            result = one;
        } // violation ''}' at column 9 should be on the same line.'
        catch (IllegalStateException ex) {
            result = two;
        }
        return result;
    }

    /**
     * Dummy method with try catch finally brace placement.
     *
     * @param one dummy first value.
     * @param two dummy second value.
     * @return chosen value.
     */
    public String load(final String one, final String two) {
        String result = one;
        try {
            result = one;
        } catch (IllegalStateException ex) {
            result = two;
        } finally {
            result = one;
        }
        try {
            result = one;
        } catch (IllegalStateException ex) {
            result = two;
        } // violation ''}' at column 9 should be on the same line.'
        finally {
            result = one;
        }
        return result;
    }

}
