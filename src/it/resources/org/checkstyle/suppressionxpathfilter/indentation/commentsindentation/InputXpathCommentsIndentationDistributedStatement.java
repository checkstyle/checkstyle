/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package org.checkstyle.suppressionxpathfilter.indentation.commentsindentation;

import java.util.Arrays;

public class InputXpathCommentsIndentationDistributedStatement {
    public void foo() {
        String s = "";
        int n = s
                    .length(); // violation below 'incorrect indentation level 24, expected is 8'
                        // Comment // warn
    }
}
