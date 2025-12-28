/*
LeftCurly
option = (default)eol
ignoreEnums = false
tokens = (default) SWITCH_RULE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlySwitchRuleWithBlock {

    void test(int x) {
        switch (x) {
            // violation below ''{' at column 23 should have line break after'
            case 1 -> { System.out.println("bad"); }
        }
    }
}
