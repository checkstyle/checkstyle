package com.sun.checkstyle.test.chapter7statements.rule75forstatements;

// violation first line 'Header mismatch'

/**
 * Test input for formatting of for statements.
 */
public final class InputForStatements {

    /**
     * Dummy method with correct for statements followed by incorrect ones.
     *
     * @param limit dummy iteration limit.
     * @return accumulated value.
     */
    public int count(final int limit) {
        int total = 0;
        int a;
        int b;
        int c;
        int d;
        int index;

        for (int i = 0; i < limit; i++) {
            total += i;
        }

        for (int i = 0, j = 0, k = 0; i < limit; i++, j++, k++) {
            total += i + j + k;
        }

        for (a = 0, b = 0, c = 0; a < limit; a++, b++, c++) {
            total += a + b + c;
        }

        for (int i = Math.min(limit, 1), j = 0, k = 0; i < limit; i++) {
            total += i + j + k;
        }

        // violation below ''for' construct must use '{}'s.'
        for (int i = 0; i < limit; i++)
            total += i;

        for (int i = 0; i < limit; i++)
        { // violation ''{' at column 9 should be on the previous line.'
            total += i;
        }

        // 2 violations 3 lines below:
        //   ''for' construct must use '{}'s.'
        //   'Empty statement.'
        for (index = 0; index < limit; index++);

        // violation below 'Avoid using more than three variables'
        for (int i = 0, j = 0, k = 0, m = 0; i < limit; i++) {
            total += i + j + k + m;
        }

        // violation below 'Avoid using more than three variables'
        for (a = 0, b = 0, c = 0, d = 0; a < limit; a++) {
            total += a + b + c + d;
        }

        // violation below 'Avoid using more than three variables'
        for (a = 0; a < limit; a++, b++, c++, d++) {
            total += a + b + c + d;
        }

        return total + index;
    }

}
