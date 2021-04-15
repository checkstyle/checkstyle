package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

/*
 * Config:
 * option = alone
 */
public class InputRightCurlyTestSinglelineIfBlocks {
    void foo1() {
        if (true) { int a = 5; } // violation

        if (true) { if (false) { int b = 6; } } // violation
    }
}
