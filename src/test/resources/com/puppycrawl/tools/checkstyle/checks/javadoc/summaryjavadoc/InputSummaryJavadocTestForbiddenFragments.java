/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = .*CheckStyle$
period = (default).


*/


package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocTestForbiddenFragments {
     // violation below 'Forbidden summary fragment'
    /**
     * This is CheckStyle. We use Java for development.
     */
    void foo1() {
    }
}
