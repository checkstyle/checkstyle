/*
RightCurly
option = ALONE
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestSinglelineIfBlocks {
    void foo1() {
        if (true) { int a = 5; } // violation ''}' at column 32 should be alone on a line'

        if (true) { if (false) { int b = 6; } } // 2 violations
    }
}
