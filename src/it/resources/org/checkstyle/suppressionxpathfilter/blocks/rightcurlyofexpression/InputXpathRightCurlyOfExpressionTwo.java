/*
RightCurlyOfExpression
option = (default)on_new_line_or_singleline


*/

package org.checkstyle.suppressionxpathfilter.blocks.rightcurlyofexpression;

public class InputXpathRightCurlyOfExpressionTwo {

    Runnable action = () -> {
        doSomething(); };
    // violation above ''}' at column 24 should be on a new line'

    void doSomething() {
    }
}
