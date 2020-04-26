package org.checkstyle.suppressionxpathfilter.nulltestaroundinstanceof;

public class SuppressionXpathRegressionNullTestAroundInstanceOf3 {
    public void test(Object myObj) {
        if ((myObj != null) && (myObj instanceof Object)) {}  // warn
    }
}
