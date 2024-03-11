package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class InputXpathUnnecessaryParentheses8 {
    void foo(int a, int b) {
        int c = (a*b); // warn
    }
}
