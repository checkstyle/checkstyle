/*
WhitespaceAfter
tokens = SINGLE_LINE_COMMENT

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterAllSlashesComment {
    //// all slashes, no following whitespace
    int num1;
    //// bad slash at end of file
}

////
//// header comment
