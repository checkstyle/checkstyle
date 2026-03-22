/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationStartOfMethodCallChainNpe {
    void foo() {
        int i = 0;
        ++
        i;
        // Comment triggers check
    }
}
