package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class InputXpathUnnecessaryParentheses4 {
    void foo (int a) {
        int b = (a) + 5; // warn
    }
}
