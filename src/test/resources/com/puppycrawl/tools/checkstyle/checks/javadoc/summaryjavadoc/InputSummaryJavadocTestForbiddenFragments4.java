/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).

*/


package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocTestForbiddenFragments4 {

    // violation below 'Forbidden summary fragment.'
    /**.
     * Return the specified member summary writer.
     *
     */
    void foo1() {}
}
