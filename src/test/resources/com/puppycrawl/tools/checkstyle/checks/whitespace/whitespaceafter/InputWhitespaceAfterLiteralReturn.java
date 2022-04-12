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

    boolean testThree() {
        return!true; // violation ''return' is not followed by whitespace'
    }

    int testFour() {
        return-1; // violation ''return' is not followed by whitespace'
    }

    double testFive() {
        return~1; // violation ''return' is not followed by whitespace'
    }
}
