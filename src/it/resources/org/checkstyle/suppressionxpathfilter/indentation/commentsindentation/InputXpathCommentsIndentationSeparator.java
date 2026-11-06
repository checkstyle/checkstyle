/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package org.checkstyle.suppressionxpathfilter.indentation.commentsindentation;

public class InputXpathCommentsIndentationSeparator {
    public void main(String[] args) {
        int n;
    } // violation 2 lines below 'incorrect indentation level 12, expected is 4'

            /////////////// Comment separator // warn

    public void foo() {
    }
}
