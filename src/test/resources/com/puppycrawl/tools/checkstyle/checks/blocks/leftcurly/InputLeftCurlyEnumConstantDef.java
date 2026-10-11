/*
LeftCurly
option = (default)eol
ignoreEnums = (default)true
tokens = ENUM_CONSTANT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public enum InputLeftCurlyEnumConstantDef {
    FIRST()
    { // violation ''{' at column 5 should be on the previous line'
    },

    SECOND
        ()
    { // violation ''{' at column 5 should be on the previous line'

    }
}
