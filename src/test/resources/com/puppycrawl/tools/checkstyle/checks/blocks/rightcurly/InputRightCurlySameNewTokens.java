/*
 * Config:
 * option = same
 * tokens = ENUM_DEF
 */

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlySameNewTokens {

    enum TestEnum{}

    enum TestEnum1{
        SOME_VALUE;} //violation

    enum TestEnum2 { SOME_VALUE; }

    enum TestEnum3{
        SOME_VALUE;
    }

    enum TestEnum4{ SOME_VALUE;
    }
}
