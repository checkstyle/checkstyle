package com.sun.checkstyle.test.chapter7statements.rule74ifstatements;

// violation first line 'Header mismatch'

/**
 * Test input with if statements missing braces incorrectly.
 */
public final class InputNeedBracesInvalid {

    /**
     * Dummy method with braceless if else statements.
     *
     * @param flag dummy flag.
     * @param one dummy first value.
     * @param two dummy second value.
     * @return chosen value.
     */
    public String pick(final boolean flag, final String one, final String two) {
        if (flag) // violation ''if' construct must use '{}'s.'
            return one;
        else // violation ''else' construct must use '{}'s.'
            return two;
    }

}
