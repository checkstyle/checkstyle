/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = ^[a-z]
period = (default).

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocInlineReturnOnlyLowercase {
    /** {@return the customer ID} */
    int customerId() {
        return 0;
    }

    /** {@return a customer object} */
    Object getCustomer() {
        return null;
    }

    /** some javadoc. */ // violation 'Forbidden summary fragment.'
    void someMethod() {
    }
}
