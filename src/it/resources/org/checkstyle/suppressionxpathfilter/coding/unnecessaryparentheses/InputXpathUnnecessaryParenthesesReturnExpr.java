package org.checkstyle.suppressionxpathfilter.coding.unnecessaryparentheses;

public class InputXpathUnnecessaryParenthesesReturnExpr {
    int foo (int a) {
        return (a+6); // warn
    }
}
