/*
WhitespaceAfter
tokens = LITERAL_CASE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralCase2 {
    public static void main(String... args) {
        switch(args[0]) {
            case"123": // violation ''case' is not followed by whitespace'
                return;
            case "1":
                return;
            default:
                return;
        }
    }
}
