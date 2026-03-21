/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

class InputSummaryJavadocInlineReturn2 {

    /**
     * // violation below 'Summary javadoc is missing.'
     * {@return <p></p>}
     */
    int returnNothing() {
        return 0;
    }
}
