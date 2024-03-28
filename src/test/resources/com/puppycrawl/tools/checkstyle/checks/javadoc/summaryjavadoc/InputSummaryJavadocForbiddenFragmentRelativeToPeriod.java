/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = ^$|fail-summary-fragment
period = _


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocForbiddenFragmentRelativeToPeriod {

    /**
     * Summary sentence on its own line_
     * <p>
     * Another sentence that is not part of the summary,
     * so this should not matter: fail-summary-fragment_
     */
    void foo1() {}

    // violation below 'Forbidden summary fragment.'
    /**
     * Summary sentence containing default period mentioning version 1.1, then ending with correct
     * period after disallowed words, fail-summary-fragment_
     */
    void foo2() {}

    // violation below 'Forbidden summary fragment.'
    /**
     * Summary sentence containing correct period mid_word, then ending with correct period after
     * disallowed words, fail-summary-fragment_
     */
    void foo3() {}
}
