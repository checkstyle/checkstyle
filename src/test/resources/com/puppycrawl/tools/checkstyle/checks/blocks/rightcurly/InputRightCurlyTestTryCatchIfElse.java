/*
RightCurly
option = (default)SAME
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestTryCatchIfElse {
    @interface TesterAnnotation {}

    void foo() throws Exception {
        int a = 90;
        boolean test = true;

        if (a == 1) {
        } else {}

        if (a == 1) {
        } else { }

        if (a == 45) {}

        // violation below ''}' at column 22 should be alone on a line'
        if (a == 9) {} else {}

        if (a == 99) {
          System.out.println("test");
        } // violation ''}' at column 9 should be on the same line as .*'
        else {
          System.out.println("before");
        }
    }
}
