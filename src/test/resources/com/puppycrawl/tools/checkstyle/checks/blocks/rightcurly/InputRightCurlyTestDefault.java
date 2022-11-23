/*
RightCurly
option = (default)SAME
tokens = (default)LITERAL_TRY , LITERAL_CATCH , LITERAL_FINALLY , LITERAL_IF , LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestDefault {

    public void foo() {
        if (456 == 123) { } // OK
        if(true) { } else { } // OK
    }

    public void foo1() {
        if (4 > 0) {
            //
        } // violation ''}' at column 9 should be on the same line as .*/else'
        else {
           //
        }
    }
}
