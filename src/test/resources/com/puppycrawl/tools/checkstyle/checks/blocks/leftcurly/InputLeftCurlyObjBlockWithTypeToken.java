/*
LeftCurly
option = (default)eol
ignoreEnums = (default)true
tokens = CLASS_DEF, OBJBLOCK


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyObjBlockWithTypeToken
{ // violation ''{' at column 1 should be on the previous line'
    class Inner
    { // violation ''{' at column 5 should be on the previous line'
    }

    class Sibling {
    }
}
