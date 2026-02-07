/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN, MARKDOWN_COMMENT


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationMarkdown {
    void foo2() {
        String s = "test";
        /// proper, indentation
        int x = 1;
/// testing
        // violation 1 lines above '.* incorrect .* level 0, expected is 8.*'
        int y = 2;
    }

    void foo3() {
        /// Good indentation
        /// more testing
        int a = 5;
    }

    /// Class level comment
    void foo4() {
        int b = 10;
    }
}
