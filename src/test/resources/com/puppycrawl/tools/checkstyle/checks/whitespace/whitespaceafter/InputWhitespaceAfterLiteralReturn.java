/*
WhitespaceAfter
tokens = LITERAL_RETURN


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralReturn {

    String testOne() {
        return ("a" + "b");
    }

    String testTwo() {
        return("a" + "b"); // violation ''return' is not followed by whitespace'
    }

    boolean testThree() {
        return!true; // violation ''return' is not followed by whitespace'
    }

    int testFour() {
        return-1; // violation ''return' is not followed by whitespace'
    }

    double testFive() {
        return~1; // violation ''return' is not followed by whitespace'
    }

    void testSix() {
        return ;
    }
}
