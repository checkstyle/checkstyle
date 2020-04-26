package org.checkstyle.suppressionxpathfilter.nullbeforeinstanceof;

public class SuppressionXpathRegressionNullBeforeInstanceOf2 {
    public void test(Object myObj) {
        if (myObj != null) { // warn
            if (myObj instanceof Object) {

            }
        }
    }
}
