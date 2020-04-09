package org.checkstyle.suppressionxpathfilter.redundantthis;

public class SuppressionXpathRegressionRedundantThisOne {
    private int age = 23;

    public void changeAge() {
        this.age = 24; //warn
    }

}
