package org.checkstyle.suppressionxpathfilter.nullbeforeinstanceof;

public class SuppressionXpathRegressionNullBeforeInstanceOf3 {
    public void test(Object myObj) {
        if ((myObj != null) && (myObj instanceof Object)) {}  // warn
    }
}
