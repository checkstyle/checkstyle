package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class InputXpathUnnecessaryParentheses2 {
    void foo(String a) {
        if (('A' == a.charAt(0))) { // warn
        }
    }
}
