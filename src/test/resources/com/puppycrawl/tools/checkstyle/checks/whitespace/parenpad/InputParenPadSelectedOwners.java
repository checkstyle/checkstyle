/*
ParenPad
option = (default)nospace
tokens = STAR, LNOT, TYPECAST

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

class InputParenPadSelectedOwners {
    int multiply(int a, int b) {
        return ( a + b) * (a - b );
        // 2 violations above:
        //  ''(' is followed by whitespace.'
        //  '')' is preceded with whitespace.'
    }

    boolean negate(boolean value) {
        return !( value );
        // 2 violations above:
        //  ''(' is followed by whitespace.'
        //  '')' is preceded with whitespace.'
    }

    Object cast(Object value) {
        return (String) ( value );
        // 2 violations above:
        //  ''(' is followed by whitespace.'
        //  '')' is preceded with whitespace.'
    }
}
