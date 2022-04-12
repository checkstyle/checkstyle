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

    interface testTwo {
        boolean is (int x);
    }

    public static testTwo Two() {
        return (x) -> true; // OK
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
        return; // OK
    }
}
