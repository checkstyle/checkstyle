package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class SuppressionXpathRegressionUnnecessaryParentheses8 {
    void foo(int a, int b) {
        int c = (a*b); // warn
    }
}
