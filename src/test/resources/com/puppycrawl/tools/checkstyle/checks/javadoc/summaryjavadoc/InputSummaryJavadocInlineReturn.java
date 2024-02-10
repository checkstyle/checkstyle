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
    private int returnFoo1()
    {
        return 0;
    }

    /**
     * {@return a {@code 0}}
     */
    private int returnFoo2()
    {
        return 0;
    }

    /**
     * Text before the return tag. {@return a {@code 0}}
     */
    private int returnFoo3() // ok, javadoc tool produces a warning
    {
        return 0;
    }

    /**
     * {@return a {@code 0}} Text after the return tag.
     */
    private int returnFoo4()
    {
        return 0;
    }

    /**
     * {@return a {@code 0}
     * there's more text on another line
     * and even more lines}
     */
    private int returnMultiline()
    {
        return 0;
    }

    /**
     * {@return a {@code 0}<br>
     * there's more text on another line<br>
     * and even more lines}
     */
    private int returnMultiline2()
    {
        return 0;
    }

    /**
     * {@return nothing, this is a void method}
     */
    private void voidMethod() // ok, javadoc tool produces an error
    {
    }

    // the return tag is empty, which is not allowed
    /**
     // violation below 'Summary javadoc is missing.'
     * {@return}
     */
    int returnNothing() {
        return 0;
    }

    /**
     * {@return nothing, this is a field}
     */
    private static final byte NOT_A_METHOD = 0; // ok, javadoc tool produces an error

    /**
     * {@return nothing, this is a class}
     */
    private class NotAMethod {} // ok, javadoc tool produces an error
}
