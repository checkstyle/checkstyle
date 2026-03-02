/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = the .*|This method returns
period = (default).

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocInlineReturnForbidden {
    /**
     * {@return the nothing}
     */
    int returnNothing() {
        return 0;
    }
    /**
     * {@return This method returns something}
     */
    int returnSomething() {
        return 0;
    }
    /**
     * {@return This method returns something.}
     */
    int returnSomethingElse() {
        return 0;
    }
}
