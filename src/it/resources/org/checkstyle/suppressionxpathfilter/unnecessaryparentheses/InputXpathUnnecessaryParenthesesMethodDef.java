package org.checkstyle.suppressionxpathfilter.unnecessaryparentheses;

public class InputXpathUnnecessaryParenthesesMethodDef {
    void foo () {
        int a = (10) + 5; // warn
    }
}
