package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class SuppressionXpathRegressionUnnecessaryParentheses2 {
    void foo(String a) {
        if (('A' == a.charAt(0))) { // warn
        }
    }
}
