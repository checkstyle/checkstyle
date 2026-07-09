/*
IllegalTokenText
format = 12
ignoreCase = (default)false
message = (default)
tokens = STRING_LITERAL, TEXT_BLOCK_CONTENT, CHAR_LITERAL


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;

public class InputIllegalTokenTextEscapeSequences2 {

    public void method() {
        String str = "\121\1812";    // violation 'Token text matches the illegal pattern '12'.'
        String a = "\12";
        String b = "\12abc";
        String c = "\121";
        String i = "\128";
        String j = "\7";
        String start = "\127";
        String end = "\n\12";
        String left = "\\12";         // violation 'Token text matches the illegal pattern '12'.'
        String right = "\\121";       // violation 'Token text matches the illegal pattern '12'.'
        String above = "\u0041\12";
        String below = "\u0041";
        String multipleU = """
                \uuuu0012
                """;

        char line = '\12';
        char lines = '\127';
        char column = '\"';
    }
}
