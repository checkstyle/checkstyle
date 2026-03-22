/*
WhitespaceAfter
tokens = LITERAL_DO


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralDo {

    boolean condition() {
        return false;
    }

    void testIfElse() {
        //Valid
        if (condition()) {
            testIfElse();
        } else {
            testIfElse();
        }

        //Invalid
        if(condition()) {
            testIfElse();
        } else {
            testIfElse();
        }

        //Invalid
        if (condition()) {
            testIfElse();
        } else{
            testIfElse();
        }
    }

    void testWhile() {
        //Valid
        while (condition()) {
            testWhile();
        }

        //Invalid
        while(condition()) {
            testWhile();
        }
    }

    void testFor() {
        //Valid
        for (int i = 0; i < 5; i++) {
            testFor();
        }

        //Invalid
        for(int i = 0; i < 5; i++) {
            testFor();
        }
    }

    void testDo() {
        //Valid
        do {
            testDo();
        } while (condition());

        //Invalid
        do{ // violation ''do' is not followed by whitespace'
            testDo();
        } while (condition());
    }
}
