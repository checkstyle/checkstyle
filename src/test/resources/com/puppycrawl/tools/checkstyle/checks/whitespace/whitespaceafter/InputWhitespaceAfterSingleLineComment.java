/*
WhitespaceAfter
tokens = SINGLE_LINE_COMMENT

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterSingleLineComment {

    void test() {
        int ok = 1; // normal trailing comment
        int missingAfter = 2; //bad
        // violation above '//' is not followed by whitespace'
        int missingAfterWithNoSpaceBefore = 3;//bad
        // violation above '//' is not followed by whitespace'
        //bad line
        // violation above '//' is not followed by whitespace'
        String emoji = "😀"; //bad
        // violation above '//' is not followed by whitespace'
        ///
        //////
        int markerOnly = 4;//
    }
}
