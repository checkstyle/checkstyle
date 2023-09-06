/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = ENUM_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyNewTokensAloneOrSingleLine {

    enum TestEnum{} // ok

    enum TestEnum1{
        SOME_VALUE;} // violation ''}' at column 20 should be alone on a line'

    enum TestEnum2 { SOME_VALUE; } // ok

    enum TestEnum3{
        SOME_VALUE;
    }

    enum TestEnum4{ SOME_VALUE;
    }
}
