package org.checkstyle.suppressionxpathfilter.coding.unnecessaryparentheses;

public class InputXpathUnnecessaryParenthesesStringLiteral {
    void foo () {
        String str = ("Checkstyle") + "is cool"; // warn
    }
}
