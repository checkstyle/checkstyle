package org.checkstyle.suppressionxpathfilter.coding.unnecessaryparentheses;

public class InputXpathUnnecessaryParenthesesConditionals {
    void foo(String a) {
        if (('A' == a.charAt(0))) { // warn
        }
    }
}
