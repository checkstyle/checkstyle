/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;





/**
 * Contains examples of using comments at the end of the block.
 */
public class InputCommentsIndentationCommentIsAtTheEndOfBlockFour {

    public void foo45() {
        int ar = 5;
        // comment
        ar = 6;
         //violation '.* incorrect .* level 9, expected is 8, .* same .* as line 21.'
    }

    public void foo46() {
// comment
// block
// violation '.* incorrect .* level 0, expected is 4, .* same .* as line 29.'
    }

    public void foo47() {
        int a = 5;
        // comment
        // block
        // comment
    }
    public void foo48() {
        int a = 5;
// comment
// block
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 38.'
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
         //violation '.* incorrect .* level 9, expected is 8, .* same .* as line 57.'
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
        //violation below '.* incorrect .* level 12, expected is 8, .* same .* as line 87.'
            /* violation */
        new Object()
            .toString();
            //violation '.* incorrect .* level 12, expected is 8, .* same .* as line 87.'
    }

    void foo56() {
        new Object().toString();
        // comment
    }

    void foo57() {
        new Object().toString();
            //violation '.* incorrect .* level 12, expected is 8, .* same .* as line 98.'
    }

    void foo58() {
        /*
           comment
           */
        //violation '.* incorrect .* level 8, expected is 10, .* same .* as line 109.'


          new InputCommentsIndentationCommentIsAtTheEndOfBlockOne().foo1();
          //comment
    }

    void foo59() {
        new InputCommentsIndentationCommentIsAtTheEndOfBlockOne().foo1();
        /*
         comment */
        // comment
    }
}
