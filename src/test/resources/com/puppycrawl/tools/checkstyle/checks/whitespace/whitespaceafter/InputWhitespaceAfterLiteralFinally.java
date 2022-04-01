/*
WhitespaceAfter
tokens = LITERAL_FINALLY


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralFinally {
    public static void main(String[] args) {

     try {}
     finally { // OK
       System.out.println("finally");}

     try {}
     finally{ // violation ''finally' is not followed by whitespace'
       System.out.println("finally");}

     try {} catch(Exception e){}
     finally { // OK
         System.out.println("finally");}

     try {} catch(Exception e){}
     finally{ // violation ''finally' is not followed by whitespace'
         System.out.println("finally");}

    }
}
