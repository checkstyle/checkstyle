/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package org.checkstyle.suppressionxpathfilter.indentation.commentsindentation;

public class InputXpathCommentsIndentationNonEmptyCase {
    int n;

    public void foo() {
        switch(n) {
            case 1:
                if (true) {} // violation below 'incorrect indentation level 19, expected is 16, 12'
                   // Comment // warn
            default:
        }
    }
}
