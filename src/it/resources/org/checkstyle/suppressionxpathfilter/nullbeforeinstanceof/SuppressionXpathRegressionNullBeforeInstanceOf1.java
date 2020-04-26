package org.checkstyle.suppressionxpathfilter.nullbeforeinstanceof;

public class SuppressionXpathRegressionNullBeforeInstanceOf1 {
    public void test(Object myObj) {
        if (myObj != null && myObj instanceof Object) { // warn

        }
    }
}
