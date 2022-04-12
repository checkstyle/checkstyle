/*
WhitespaceAfter
tokens = LITERAL_RETURN


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralReturn {

    interface test {
        int add (int x);
    }

    public static test One() {
        return(x) -> x; // violation ''return' is not followed by whitespace'
    }

    public static test Two() {
        return (x) -> x; // OK
    }

     void testThree () {
        return; // OK
    }

    boolean testFour() {
        return!true; // violation ''return' is not followed by whitespace'
    }

    int testFive() {
        return-1; // violation ''return' is not followed by whitespace'
    }

    double testSix() {
        return~1; // violation ''return' is not followed by whitespace'
    }
}
