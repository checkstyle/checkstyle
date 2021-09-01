/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = @return the .*|This method returns
period = (default).

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocInlineReturnForbidden {
    // violation below
    /**
     * {@return}
     */
    int returnNothing() {
        return 0;
    }
    // violation below
    /**
     * {@return This method returns something}
     */
    int returnSomething() {
        return 0;
    }
    // violation below
    /**
     * {@return This method returns something.}
     */
    int returnSomethingElse() {
        return 0;
    }
}
