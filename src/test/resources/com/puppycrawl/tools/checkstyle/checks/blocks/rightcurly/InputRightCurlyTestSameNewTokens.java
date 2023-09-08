/*
RightCurly
option = (default)SAME
tokens = ENUM_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestSameNewTokens {

    enum TestEnum{}

    enum TestEnum1{
        SOME_VALUE;} // violation ''}' at column 20 should have line break before'

    enum TestEnum2 { SOME_VALUE; }

    enum TestEnum3{
        SOME_VALUE;
    }

    enum TestEnum4{ SOME_VALUE;
    }
}
