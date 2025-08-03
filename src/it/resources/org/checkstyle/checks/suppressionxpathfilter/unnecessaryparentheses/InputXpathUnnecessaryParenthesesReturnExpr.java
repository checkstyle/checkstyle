package org.checkstyle.checks.suppressionxpathfilter.unnecessaryparentheses;

public class InputXpathUnnecessaryParenthesesReturnExpr {
    int foo (int a) {
        return (a+6); // warn
    }
}
