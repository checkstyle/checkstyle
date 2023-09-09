/*
LeftCurly
option = \tNL
tokens = STATIC_INIT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyWithTrimOptionProperty {

    static { // violation ''{' at column 12 should be on a new line'
    }
    static
    {}

    static class Inner
    {
        static { // violation ''{' at column 16 should be on a new line'
            int i = 1;
        }
    }
}
