/*
WhitespaceAfter
tokens = SINGLE_LINE_COMMENT

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterSingleLineComment {
    // violation below ''//' is not followed by whitespace.'
    //invalid
    // valid
    //	valid tab
    //
    /// doc comment
    //// header comment
}
//
