/*
SummaryJavadoc
violateExecutionOnNonTightHtml = true
forbiddenSummaryFragments = ^Returns the customer ID. This method returns.$
period = .

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadoc1 {

    // violation 2 lines below 'd'
    /**
     * {@summary Returns the customer ID.
     *  This method returns. }
     */
    public void foo() {
    }
}
