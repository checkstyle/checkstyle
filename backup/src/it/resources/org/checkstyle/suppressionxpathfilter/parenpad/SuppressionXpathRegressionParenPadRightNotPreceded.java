package org.checkstyle.suppressionxpathfilter.parenpad;

public class SuppressionXpathRegressionParenPadRightNotPreceded{
    void method() {
        if ( false) {//warn
        }
        if ( true ) {
        }
    }
}
