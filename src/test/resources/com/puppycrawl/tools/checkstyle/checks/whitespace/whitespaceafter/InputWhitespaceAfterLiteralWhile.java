/*
WhitespaceAfter
tokens = LITERAL_WHILE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralWhile {

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
        while(condition()) { // violation ''while' is not followed by whitespace'
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
        do{
            testDo();
        } while (condition());
    }
}
