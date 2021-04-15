package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

/*
 * Config:
 * option = alone_or_singleline
 * tokens = { ENUM_DEF }
 */
public class InputRightCurlyNewTokensAloneOrSingleLine {

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
