/*
RightCurly
option = ALONE
tokens = LITERAL_IF, LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestIfElseAlone {

    public void test() {
       int id = 0;
       switch (id) {
           case 0: break;
           case 1: if (1 == 0) {
               break;
           }; // violation ''}' at column 12 should be alone on a line.'
           case 2: break;
       }
    }

    public void test2() {
        if(true) {

        } if(false) { // violation ''}' at column 9 should be alone on a line.'

        }
    }

}
