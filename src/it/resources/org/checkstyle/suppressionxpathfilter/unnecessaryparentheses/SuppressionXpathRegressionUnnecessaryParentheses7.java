package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class SuppressionXpathRegressionUnnecessaryParentheses7 {
    int foo (int a) {
        return (a+6); // warn
    }
}
