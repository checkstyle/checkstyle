package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class SuppressionXpathRegressionUnnecessaryParentheses4 {
    void foo (int a) {
        int b = (a) + 5; // warn
    }
}
