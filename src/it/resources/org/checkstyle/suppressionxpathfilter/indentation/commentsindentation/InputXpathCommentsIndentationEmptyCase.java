/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package org.checkstyle.suppressionxpathfilter.indentation.commentsindentation;

public class InputXpathCommentsIndentationEmptyCase {
    int n;

    public void foo() {
        switch(n) {
            case 1: // violation below 'incorrect indentation level 0, expected is 12, 12'
// Comment // warn
            default:
        }
    }
}
