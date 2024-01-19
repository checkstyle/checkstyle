/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN

*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;
/**
 * Contains examples of using comments at the end of the block.
 */
public class InputCommentsIndentationCommentIsAtTheEndOfBlockFour {
    public void foo48() {
        int a = 5;
// comment
// block
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 340.'
    }

    public void foo49() {
        // comment
        // block
        // ok
    }

    public void foo50() {
        return;

        // No NPE here!
    }

    public String foo51() {
        return String
                .valueOf("11"
                );
        // violation '.* incorrect .* level 9, expected is 8, .* same .* as line 359.'
    }

    public String foo52() {
        return String
                .valueOf("11"
                );
        // comment
    }

    void foo53() {
        // comment
        new Object()
                .toString();
        // comment
    }

    void foo54() {
        /* comment */
        new Object()
                .toString();
        // comment
    }

    void foo55() {
        // violation below '.* incorrect .* level 12, expected is 8, .* same .* as line 389.'
        /* violation */
        new Object()
                .toString();
        // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 389.'
    }

    void foo56() {
        new Object().toString();
        // comment
    }

    void foo57() {
        new Object().toString();
        // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 400.'
    }

    void foo58() {
        /*
           comment
           */
        // violation '.* incorrect .* level 8, expected is 10, .* same .* as line 409.'
        InputCommentsIndentationCommentIsAtTheEndOfBlockOne a = new InputCommentsIndentationCommentIsAtTheEndOfBlockOne();
        a.foo1();
        // comment
    }

    void foo59() {
        InputCommentsIndentationCommentIsAtTheEndOfBlockOne a = new InputCommentsIndentationCommentIsAtTheEndOfBlockOne();
        a.foo1();
        /*
         comment */
        // comment
    }


    void foo61() {
        InputCommentsIndentationCommentIsAtTheEndOfBlockOne a = new InputCommentsIndentationCommentIsAtTheEndOfBlockOne();
        a.foo1();
        /*
         * comment
         */
        /*
         * comment
         */
    }
    void foo62() {
        if (true) {
            String.CASE_INSENSITIVE_ORDER.equals("");
        }
        else {

        }
        /*
         comment
         */
        /*
         comment
         */
    }
}
