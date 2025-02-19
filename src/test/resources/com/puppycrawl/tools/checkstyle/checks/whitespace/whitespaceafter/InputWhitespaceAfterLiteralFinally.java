/*
WhitespaceAfter
tokens = LITERAL_FINALLY


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralFinally {
    public static void main(String[] args) {

     try {} finally {}
     try {} finally{} // violation ''finally' is not followed by whitespace'

     try {} catch (Error e){} finally {}
     try {} catch (Error e){} finally{} // violation''finally' is not followed by whitespace'
    }
}
