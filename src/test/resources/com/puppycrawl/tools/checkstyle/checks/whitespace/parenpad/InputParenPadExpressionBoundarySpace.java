/*
ParenPad
option = space
tokens = EXPR

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

class InputParenPadExpressionBoundarySpace {
    int multiply(int a, int b) {
        return (a + b) * (a - b);
    }

    boolean negate(boolean value) {
        return !(value);
    }

    Object cast(Object value) {
        return (String)(value);
    }
    int direct(int value) {
        return (value);
        // 2 violations above:
        //  ''(' is not followed by whitespace.'
        //  '')' is not preceded with whitespace.'
    }
}
