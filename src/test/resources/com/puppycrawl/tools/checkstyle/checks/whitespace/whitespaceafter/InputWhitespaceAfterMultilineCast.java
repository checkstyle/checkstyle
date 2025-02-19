/*
WhitespaceAfter
tokens = TYPECAST


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterMultilineCast {
    void issue3850() {
        Object obj = new Object();
        obj = (java.lang.
                Object)obj; // violation ''typecast' is not followed by whitespace'
        obj = (java.lang.
                Object) obj;
    }
}
