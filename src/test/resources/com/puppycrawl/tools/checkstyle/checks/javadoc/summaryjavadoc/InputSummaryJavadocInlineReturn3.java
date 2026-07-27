/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = ^@return the *|^This method returns |^A \
                            [{]@code [a-zA-Z0-9]+[}]( is a )
period = (default).

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/** Some javadoc. */
public class InputSummaryJavadocInlineReturn3 {

    /** {@return the customer ID} */
    int returnNumber() {
        return 0;
    }
}
