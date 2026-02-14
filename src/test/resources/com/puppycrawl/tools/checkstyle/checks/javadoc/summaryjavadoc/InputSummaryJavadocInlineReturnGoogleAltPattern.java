/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = ^@return the *|^[a-z]|^This method returns
period = (default).

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocInlineReturnGoogleAltPattern {
    /** {@return the customer ID} */
    int customerId() {
        return 0;
    }

    /** {@return a customer object} */
    Object getCustomer() {
        return null;
    }

    /** {@return This method returns something} */
    int returnSomething() {
        return 0;
    }
}
