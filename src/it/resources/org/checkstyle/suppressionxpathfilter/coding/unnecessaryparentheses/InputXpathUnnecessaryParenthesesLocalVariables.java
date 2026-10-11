package org.checkstyle.suppressionxpathfilter.coding.unnecessaryparentheses;

public class InputXpathUnnecessaryParenthesesLocalVariables {
    void foo (int a) {
        int b = (a) + 5; // warn
    }
}
