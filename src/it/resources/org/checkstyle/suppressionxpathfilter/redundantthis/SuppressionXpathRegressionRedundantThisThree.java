package org.checkstyle.suppressionxpathfilter.redundantthis;

public class SuppressionXpathRegressionRedundantThisThree {
    private int x;

    void method1() {
        this.x++; // warn
        this.method2();

    }

    void method2() {}
}
