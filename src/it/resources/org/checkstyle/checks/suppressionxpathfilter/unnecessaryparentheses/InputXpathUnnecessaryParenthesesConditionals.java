package org.checkstyle.checks.suppressionxpathfilter.unnecessaryparentheses;

public class InputXpathUnnecessaryParenthesesConditionals {
    void foo(String a) {
        if (('A' == a.charAt(0))) { // warn
        }
    }
}
