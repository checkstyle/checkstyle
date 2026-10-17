/*
ParenPad
option = (default)nospace
tokens = EXPR

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

class InputParenPadExpressionBoundary {
    int multiply(int a, int b) {
        return ( a + b) * (a - b );
    }

    boolean negate(boolean value) {
        return !( value );
    }

    Object cast(Object value) {
        return (String) ( value );
    }
    int direct(int value) {
        return ( value );
        // 2 violations above:
        //  ''(' is followed by whitespace.'
        //  '')' is preceded with whitespace.'
    }
}
