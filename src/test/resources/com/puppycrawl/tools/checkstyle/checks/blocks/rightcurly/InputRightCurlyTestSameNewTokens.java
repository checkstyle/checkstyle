package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

/*
 * Config:
 * option = same
 * tokens = { ENUM_DEF }
 */
public class InputRightCurlyTestSameNewTokens {

    enum TestEnum{} // ok

    enum TestEnum1{
        SOME_VALUE;} // violation

    enum TestEnum2 { SOME_VALUE; } // ok

    enum TestEnum3{
        SOME_VALUE;
    }

    enum TestEnum4{ SOME_VALUE;
    }
}
