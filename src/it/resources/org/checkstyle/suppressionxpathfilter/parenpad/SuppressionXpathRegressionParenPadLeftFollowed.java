package org.checkstyle.suppressionxpathfilter.parenpad;

public class SuppressionXpathRegressionParenPadLeftFollowed {
    void method() {
        if ( false) {//warn
        }
        if (true) {
        }
    }
}
