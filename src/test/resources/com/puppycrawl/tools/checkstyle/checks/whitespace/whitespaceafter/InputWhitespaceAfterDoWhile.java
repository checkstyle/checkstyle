package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterDoWhile {

    boolean condition() {
        return false;
    }

    void testDoWhile() {
        //Valid
        do {
            testDoWhile();
        } while (condition());

        //Invalid
        do {
            testDoWhile();
        } while(condition());            //violation
    }
}
