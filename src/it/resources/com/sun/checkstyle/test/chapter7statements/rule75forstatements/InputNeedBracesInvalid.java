package com.sun.checkstyle.test.chapter7statements.rule75forstatements;

// violation first line 'Header mismatch'

/**
 * Test input with for statements missing braces incorrectly.
 */
public final class InputNeedBracesInvalid {

    /**
     * Dummy method with a braceless for statement.
     *
     * @param limit dummy iteration limit.
     * @return accumulated value.
     */
    public int count(final int limit) {
        int total = 0;
        // violation below ''for' construct must use '{}'s.'
        for (int index = 0; index < limit; index++)
            total += index;
        return total;
    }

    /**
     * Dummy method with an empty for statement, which Sun allows but
     * sun_checks.xml reports.
     *
     * @param limit dummy iteration limit.
     * @return last index.
     */
    public int last(final int limit) {
        int index;
        // 2 violations 3 lines below:
        //   ''for' construct must use '{}'s.'
        //   'Empty statement.'
        for (index = 0; index < limit; index++);
        return index;
    }

}
