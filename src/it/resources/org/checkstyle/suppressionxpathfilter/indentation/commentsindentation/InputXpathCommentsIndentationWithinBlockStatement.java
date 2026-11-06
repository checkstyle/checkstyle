/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package org.checkstyle.suppressionxpathfilter.indentation.commentsindentation;

public class InputXpathCommentsIndentationWithinBlockStatement {
    public void foo() {
        String s = "F" // violation below 'incorrect indentation level 8, expected is 12'
        // Comment // warn
            + "O"
            + "O";
    }
}
