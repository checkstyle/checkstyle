/*
WhitespaceAfter
tokens = LITERAL_CASE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralCase {
    public static void main(String... args) {
        switch(args[0]) {
            case "123":
                return;
            case"1": // violation ''case' is not followed by whitespace'
                return;
            default:
                return;
        }
    }
}
