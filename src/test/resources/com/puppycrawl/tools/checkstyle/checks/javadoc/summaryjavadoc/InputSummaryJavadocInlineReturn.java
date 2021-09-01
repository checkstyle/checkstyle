/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

class InputSummaryJavadocInlineReturn {
    /**
     * {@return a zero, note that no period is ok as it is set by javadoc tool}
     */
    private int returnFoo1() // ok
    {
        return 0;
    }

    /**
     * {@return a {@code 0}}
     */
    private int returnFoo2() // ok
    {
        return 0;
    }

    // violation below
    /**
     * {@return}
     */
    int returnNothing() {
        return 0;
    }

    /**
     * {@return nothing, this is a field}
     */
    private static final byte NOT_A_METHOD = 0; // ok

    /**
     * {@return nothing, this is a class}
     */
    private class NotAMethod {} // ok
}
