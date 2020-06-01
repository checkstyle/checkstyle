package org.checkstyle.suppressionxpathfilter.nulltestaroundinstanceof;

public class SuppressionXpathRegressionNullTestAroundInstanceOf2 {
    public void test(Object myObj) {
        if (myObj != null) { // warn
            if (myObj instanceof Object) {

            }
        }
    }
}
