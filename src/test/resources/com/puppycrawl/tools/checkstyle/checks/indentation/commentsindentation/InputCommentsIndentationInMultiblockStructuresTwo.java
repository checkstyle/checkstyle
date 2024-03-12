/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationInMultiblockStructuresTwo {
    void foo() {

        do {
            assert true;
        // comment for while
        } while (false);

        do {
            assert true;
        // violation '.* incorrect .* level 8, expected is 12, .* same .* as line 19.'
        }
        while (false);

        do {
            assert true;
                // violation '.* incorrect .* level 16, expected is 12, 8,.*same.*as line 25, 27.'
        } while (false);

        do {
            assert true;
// violation '.* incorrect .* level 0, expected is 12, 8, .* same .* as line 30, 32.'
        } while (false);
    }
}
