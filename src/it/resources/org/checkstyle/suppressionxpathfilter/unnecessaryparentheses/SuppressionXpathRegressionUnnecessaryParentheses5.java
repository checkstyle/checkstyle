package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class SuppressionXpathRegressionUnnecessaryParentheses5 {
    void foo () {
        String str = ("Checkstyle") + "is cool"; // warn
    }
}
