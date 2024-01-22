/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationInMultiblockStructures {
    void foo() {

        do {
            assert true;
        // comment for while
        } while (false);

        do {
            assert true;
        // violation '.* incorrect .* level 8, expected is 12, .* same .* as line 123.'
        }
        while (false);

        do {
            assert true;
                // violation '.* incorrect .* level 16, expected is 12, 8,.*same.*as line 129, 131.'
        } while (false);

        do {
            assert true;
// violation '.* incorrect .* level 0, expected is 12, 8, .* same .* as line 134, 136.'
        } while (false);
    }
}
