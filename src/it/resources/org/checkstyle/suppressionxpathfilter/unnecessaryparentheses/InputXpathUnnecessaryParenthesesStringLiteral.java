package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class InputXpathUnnecessaryParenthesesStringLiteral {
    void foo () {
        String str = ("Checkstyle") + "is cool"; // warn
    }
}
