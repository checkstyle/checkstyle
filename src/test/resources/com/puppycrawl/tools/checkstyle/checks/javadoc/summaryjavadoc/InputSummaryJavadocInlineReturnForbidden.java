/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = the .*|This method returns
period = (default).

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocInlineReturnForbidden {
    /**
     // violation below 'Forbidden summary fragment.'
     * {@return the nothing}
     */
    int returnNothing() {
        return 0;
    }
    /**
     // violation below 'Forbidden summary fragment.'
     * {@return This method returns something}
     */
    int returnSomething() {
        return 0;
    }
    /**
     // violation below 'Forbidden summary fragment.'
     * {@return This method returns something.}
     */
    int returnSomethingElse() {
        return 0;
    }
}
