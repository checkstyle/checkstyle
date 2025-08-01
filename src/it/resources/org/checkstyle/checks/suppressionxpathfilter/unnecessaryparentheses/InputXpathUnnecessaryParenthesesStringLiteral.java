package org.checkstyle.checks.suppressionxpathfilter.unnecessaryparentheses;

public class InputXpathUnnecessaryParenthesesStringLiteral {
    void foo () {
        String str = ("Checkstyle") + "is cool"; // warn
    }
}
