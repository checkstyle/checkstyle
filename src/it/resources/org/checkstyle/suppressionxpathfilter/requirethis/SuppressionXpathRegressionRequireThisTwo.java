package org.checkstyle.suppressionxpathfilter.requirethis;

public class SuppressionXpathRegressionRequireThisTwo {
    void method1() {
        int i = 3;
    }

    void method2(int i) {
        method1(); //warn
    }
}
