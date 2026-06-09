/*
RightCurlyAloneOrEmpty
tokens = LITERAL_SWITCH, LITERAL_CASE, LITERAL_DEFAULT
allowMultiBlock = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

public class InputRightCurlyAloneOrEmptySwitchNewStyle {

    public void method() {
        int x = 0;
        int mode = 0;

        switch (mode) {
            case 1 -> x = 1;
            case 2 -> {}
            case 3 -> { x = 3; }
            default -> x = 0;
        }
        // violation 3 lines above ''}' at column 32 should be alone on a line'

        int result = switch (mode) {
            case 1 -> 1;
            case 2 -> { yield 2; }
            default -> { yield
                0; }
        };
        // violation 4 lines above ''}' at column 34 should be alone on a line'
        // violation 3 lines above ''}' at column 20 should be alone on a line'
    }
}
