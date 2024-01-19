/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN

*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;
/**
 * Contains examples of using comments at the end of the block.
 */
public class InputCommentsIndentationCommentIsAtTheEndOfBlockFive {
    void foo63() {
        try {
            String.CASE_INSENSITIVE_ORDER.equals("");
        }
        catch (Exception e){

        }

        /*
         comment
         */
        /*
         comment
         */
    }

    void foo64()  {
        InputCommentsIndentationCommentIsAtTheEndOfBlockOne a = new InputCommentsIndentationCommentIsAtTheEndOfBlockOne();
        a.foo1();

//  violation '.* incorrect .* level 0, expected is 8, .* same .* as line 463.'
    }

    void foo65() {
        int i = 1
                + 1
                + 1;
        // comment
        // comment
    }

    void foo66()  {
        if (true) {
            getClass();
        }
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 477.'
        /* violation */
    }

    void foo67()  {
        try {
            getClass();
        } finally {
            hashCode();
        }
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 485.'
        /* violation */
    }

    void foo68()  {
        for (int i = 0; i < 0; i++) {
            getClass();
        }
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 495.'
        /* violation */
    }

    void foo69()  {
        while (true) {
            getClass();
        }
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 503.'
        /* violation */
    }

    void foo70()  {
        do {
            getClass();
        } while (true);
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 511.'
        /* violation */
    }

    void foo71() {
        switch("") {
            case "!":
                break;
            default:
                break;
        }

        // violation '.* incorrect .* level 10, expected is 8, .* same .* as line 519.'
    }

    void foo72() {
        int u = 1;

        /* comment */
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 530.'
    }
    void foo73() {
        class Foo { }

        /* comment */
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 537.'
    }

}
