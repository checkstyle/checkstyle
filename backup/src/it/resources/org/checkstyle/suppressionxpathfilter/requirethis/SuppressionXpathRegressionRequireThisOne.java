package org.checkstyle.suppressionxpathfilter.requirethis;

public class SuppressionXpathRegressionRequireThisOne {
    private int age = 23;

    public void changeAge() {
        age = 24; //warn
    }
}
