/*
LeftCurly
option = (default)eol
ignoreEnums = false
tokens = SWITCH_RULE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlySwitchMutation {

    void test(int x) {
        int result = switch (x) {
            // violation below ''{' at column 23 should have line break after'
            case 1 -> { yield 1; }
            // violation below ''{' at column 24 should have line break after'
            default -> { yield 0; }
        };
    }

}
