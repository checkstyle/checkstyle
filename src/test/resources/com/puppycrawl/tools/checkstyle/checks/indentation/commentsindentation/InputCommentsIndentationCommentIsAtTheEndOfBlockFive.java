/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;





/**
 * Contains examples of using comments at the end of the block.
 */
public class InputCommentsIndentationCommentIsAtTheEndOfBlockFive {

    void foo61() {
        new InputCommentsIndentationCommentIsAtTheEndOfBlockOne().foo1();

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
        new InputCommentsIndentationCommentIsAtTheEndOfBlockOne().foo1();

//  violation '.* incorrect .* level 0, expected is 8, .* same .* as line 59.'
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
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 73.'
        /* violation */
    }

    void foo67()  {
        try {
            getClass();
        } finally {
            hashCode();
        }
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 81.'
        /* violation */
    }

    void foo68()  {
        for (int i = 0; i < 0; i++) {
            getClass();
        }
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 91.'
        /* violation */
    }

    void foo69()  {
        while (true) {
            getClass();
        }
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 99.'
        /* violation */
    }

    void foo70()  {
        do {
            getClass();
        } while (true);
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 107.'
        /* violation */
    }

}
