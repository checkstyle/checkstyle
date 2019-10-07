/*
 * Config:
 * option = alone
 * tokens = ENUM_DEF
 */

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyAloneNewTokens {

    enum TestEnum{} //violation

    enum TestEnum1{
        SOME_VALUE;} //violation

    enum TestEnum2 { SOME_VALUE; } //violation

    enum TestEnum3{
        SOME_VALUE;
    }

    enum TestEnum4{ SOME_VALUE;
    }
}
