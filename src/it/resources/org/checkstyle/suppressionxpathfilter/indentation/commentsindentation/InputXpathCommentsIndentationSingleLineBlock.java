/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package org.checkstyle.suppressionxpathfilter.indentation.commentsindentation;

public class InputXpathCommentsIndentationSingleLineBlock {
    public void foo() { // violation 2 lines below 'incorrect indentation level 0, expected is 4'
// Single Line
// block Comment // warn
    }
}
