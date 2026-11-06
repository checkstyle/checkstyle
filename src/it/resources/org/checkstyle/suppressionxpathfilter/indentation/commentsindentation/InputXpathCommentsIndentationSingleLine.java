/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package org.checkstyle.suppressionxpathfilter.indentation.commentsindentation;

public class InputXpathCommentsIndentationSingleLine {
    int n; // violation below 'incorrect indentation level 8, expected is 4'
        // Comment // warn
}
