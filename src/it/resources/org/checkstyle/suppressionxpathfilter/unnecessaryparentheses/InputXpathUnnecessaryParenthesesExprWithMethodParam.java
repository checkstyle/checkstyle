package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class InputXpathUnnecessaryParenthesesExprWithMethodParam {
    void foo(int a, int b) {
        int c = (a*b); // warn
    }
}
