package com.puppycrawl.tools.checkstyle.checks.whitespace;

public class InputDoWhile {

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
