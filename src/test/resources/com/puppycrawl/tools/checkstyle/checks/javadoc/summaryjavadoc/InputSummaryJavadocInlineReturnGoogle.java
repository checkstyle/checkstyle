/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = ^@return the *|^This method returns |^A \
                            [{]@code [a-zA-Z0-9]+[}]( is a )|^[a-z]
period = (default).

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocInlineReturnGoogle {
    /**
     * {@return the customer ID}
     */
    int customerId() {
        return 0;
    }

    /**
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
     * {@return a customer object}
     */
    Object getCustomer() {
        return null;
    }

    /**
     // violation below 'Forbidden summary fragment.'
     * some javadoc
     */
    void someMethod() {
    }
}
