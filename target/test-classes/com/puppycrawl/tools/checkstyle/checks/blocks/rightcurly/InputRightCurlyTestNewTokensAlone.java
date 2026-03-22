/*
RightCurly
option = ALONE
tokens = ENUM_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestNewTokensAlone {

    enum TestEnum{} // violation ''}' at column 19 should be alone on a line'

    enum TestEnum1{
        SOME_VALUE;} // violation ''}' at column 20 should be alone on a line'

    enum TestEnum2 { SOME_VALUE; } // violation ''}' at column 34 should be alone on a line'

    enum TestEnum3{
        SOME_VALUE;
    }

    enum TestEnum4{ SOME_VALUE;
    }
}
