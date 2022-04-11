/*
WhitespaceAfter
tokens = LITERAL_RETURN


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralReturn {
    public int testOne (int x){
        return (x * x); // OK
    }
    public int testTwo (int x){
        return(x * x); // violation ''return' is not followed by whitespace'
    }
}
