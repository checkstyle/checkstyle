package org.checkstyle.suppressionxpathfilter.redundantthis;

public class SuppressionXpathRegressionRedundantThisTwo {
    private int x;

    void method1() {
        this.x++;
        this.method2(); // warn

    }

    void method2() {}
}
