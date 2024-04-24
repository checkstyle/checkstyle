package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class InputXpathUnnecessaryParenthesesConditionals {
    void foo(String a) {
        if (('A' == a.charAt(0))) { // warn
        }
    }
}
