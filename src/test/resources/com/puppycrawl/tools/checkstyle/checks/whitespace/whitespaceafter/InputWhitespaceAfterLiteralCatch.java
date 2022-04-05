/*
WhitespaceAfter
tokens = LITERAL_CATCH


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralCatch {
    public static void main(String[] args) {
        try {
            Integer.parseInt(args[0]);
        } catch (NumberFormatException e) { // OK
            System.out.println("not a number");
        }

        try {
            Integer.parseInt(args[0]);
        } catch(NumberFormatException e) { // violation ''catch' is not followed by whitespace'
            System.out.println("not a number");
        }

        try {}catch (Exception e){} // OK

        try {}catch(Exception e){} // violation ''catch' is not followed by whitespace'
    }
}
