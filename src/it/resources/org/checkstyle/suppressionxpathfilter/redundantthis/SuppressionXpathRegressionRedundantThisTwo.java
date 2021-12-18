package org.checkstyle.suppressionxpathfilter.redundantthis;

public class SuppressionXpathRegressionRedundantThisTwo {
    private int x;

    void method1() {
        x++;
        this.method2(); // warn

    }

    void method2() {}
}