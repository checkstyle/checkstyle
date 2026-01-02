/*
LeftCurly
option = nl
ignoreEnums = false
tokens = LITERAL_CASE, LITERAL_DEFAULT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlySwitchRuleWithBlock {

    void test(int x) {
        int result = switch (x) {
            // violation below ''{' at column 23 should be on a new line'
            case 1 -> { yield 1; }
            default -> 0;
        };
    }
}
