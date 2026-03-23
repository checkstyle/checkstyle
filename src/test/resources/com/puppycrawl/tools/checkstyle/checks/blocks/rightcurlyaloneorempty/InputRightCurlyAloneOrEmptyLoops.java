/*
RightCurlyAloneOrEmpty
tokens = LITERAL_FOR, LITERAL_WHILE
allowMultiBlock = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

public class InputRightCurlyAloneOrEmptyLoops {

    public void method() {
        int x = 0;
        int mode = 0;

        // while
        while (mode == 1)
            x = 1;

        while (mode == 1) { }

        while (mode == 1) {
            // comment
        }

        while (mode == 1) {
            x = 1;
        } // comment
        // violation above ''}' at column 9 should be alone on a line'

        while (mode == 1) { x = 1;
        }

        do
            x = 1;
        while (mode == 1);

        for (int i = 0; i < 10; i++)
            x = i;

        for (int i = 0; i < 10; i++) {
        }

        for (int i = 0; i < 10; i++) {
            x = i;
        } // comment
        // violation above ''}' at column 9 should be alone on a line'

    }
}
