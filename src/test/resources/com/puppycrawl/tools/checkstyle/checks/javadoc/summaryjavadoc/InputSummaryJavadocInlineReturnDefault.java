/*
SummaryJavadoc

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocInlineReturnDefault {
    /** {@return the customer ID} */
    int customerId() {
        return 0;
    }

    /** {@return a customer object} */
    Object getCustomer() {
        return null;
    }
}
