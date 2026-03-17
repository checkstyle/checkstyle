/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = the .*|This method returns
forbiddenInlineReturnFragments = the .*|This method returns
period = (default).

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocInlineReturnForbidden {
    /**
     * {@return the nothing} // violation 'Forbidden summary fragment.'
     */
    int returnNothing() {
        return 0;
    }
    /**
     * {@return This method returns something} // violation 'Forbidden summary fragment.'
     */
    int returnSomething() {
        return 0;
    }
    /**
     * {@return This method returns something.} // violation 'Forbidden summary fragment.'
     */
    int returnSomethingElse() {
        return 0;
    }
}
