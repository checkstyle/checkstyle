package org.checkstyle.suppressionxpathfilter.nulltestaroundinstanceof;

public class SuppressionXpathRegressionNullTestAroundInstanceOf1 {
    public void test(Object myObj) {
        if (myObj != null && myObj instanceof Object) { // warn

        }
    }
}
