/*
WhitespaceAfter
tokens = LITERAL_RETURN


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralReturn {

    int testOne (int x){
        return (testOne(5)); // OK
    }

    int testTwo (int x){
        return(testTwo(3)); // violation ''return' is not followed by whitespace'
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
