package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class InputXpathUnnecessaryParenthesesLocalVariables {
    void foo (int a) {
        int b = (a) + 5; // warn
    }
}
