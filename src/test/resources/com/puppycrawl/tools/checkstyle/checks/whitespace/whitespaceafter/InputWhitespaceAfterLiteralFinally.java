/*
WhitespaceAfter
tokens = LITERAL_FINALLY


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralFinally {
    public static void main(String[] args) {

     try {}
     finally { System.out.println("Hello"); } // OK

     try {}
     finally{ System.out.println("Hi"); } // violation ''finally' is not followed by whitespace'

     try {} catch (Exception e){}
     finally { System.out.println("Hello"); } // OK

     try {} catch (Exception e){}
     finally{ System.out.println("Hi"); } // violation ''finally' is not followed by whitespace'
    }
}
