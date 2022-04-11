/*
WhitespaceAfter
tokens = LITERAL_RETURN


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralReturn {

    int testOne (int x){
        return(x * x); // violation ''return' is not followed by whitespace'
    }

    int testTwo (int x){
        return(x * x); // violation ''return' is not followed by whitespace'
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
