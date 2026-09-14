/*
WhitespaceAfter
tokens = SINGLE_LINE_COMMENT, SEMI

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterSingleLineComment {
    // violation below ''//' is not followed by whitespace.'
    //invalid

    void testSlash() {
        int num1; // Good Slash
        int num2;// Bad Slash
        // violation above '';' is not followed by whitespace.'
        int num3; //Bad Slash
        // violation above ''//' is not followed by whitespace.'
        int num4;//Bad Slash
        // 2 violations above:
        // '';' is not followed by whitespace.'
        // ''//' is not followed by whitespace.'
    } //Bad Slash
    // violation above ''//' is not followed by whitespace.'

    /// doc comment
    // violation below ''//' is not followed by whitespace.'
    ///doc comment
    //// header comment
    //
    ///
}
