/*
RightCurlyOfExpression
option = on_same_line


*/

package org.checkstyle.suppressionxpathfilter.blocks.rightcurlyofexpression;

public class InputXpathRightCurlyOfExpressionThree {

    Object anon = new Object() {
        int b = 10;
    };
    // violation above ''}' at column 5 should be on the previous line'
}
