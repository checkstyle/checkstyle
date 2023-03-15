package org.checkstyle.suppressionxpathfilter.parenpad;

public class SuppressionXpathRegressionParenPadLeftNotFollowed {
    void method() {
        if (false ) {//warn
        }
        if ( true ) {
        }
    }
}
