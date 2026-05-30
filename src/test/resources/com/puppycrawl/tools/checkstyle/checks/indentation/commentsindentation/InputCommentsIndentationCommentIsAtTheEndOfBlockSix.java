/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;





/**
 * Contains examples of using comments at the end of the block.
 */
public class InputCommentsIndentationCommentIsAtTheEndOfBlockSix {

    void foo71() {
        switch("") {
            case "!":
                break;
            default:
                break;
        }

          // violation '.* incorrect .* level 10, expected is 8, .* same .* as line 19.'
    }

    void foo72() {
        int u = 1;

        /* comment */
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 30.'
    }
    void foo73() {
        class Foo { }

        /* comment */
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 36.'
    }

    interface Bar1 {
        interface NestedBar { }

// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 43.'
    }
    static class Bar2 {
        enum Foo {
            A;
        }

    // violation '.* incorrect .* level 4, expected is 8, .* same .* as line 48.'
    }
    static class Bar3 {
        // violation 2 lines below '.* incorrect .* level 12, expected is 8, .* same .* as line 56.'
        @interface Foo { }
            // end of block
    }

    void foo74() {
        getClass(); // comment
// comment
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 61.'
    }

    void foo75() {
        getClass();
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 67.'
        // comment
    }

    void InputCommentsIndentationCommentIsAtTheEndOfBlock(String s) {
        assert(s == null ||
                s != null);
        // comment
        //comment
    }

    int foo76() {
        return 0;
        /* test
         * test */
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 80.'
    }

    void foo77() {
        try {
            /* CHECKSTYLE:OFF */} catch(Exception e) {
        }
    }
    void foo78() {
        /* violation */
        new Object()
                .toString();
            // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 93.'
    }
    void foo79() {
        /* violation */
        /* violation */new Object().toString();

    }

    // We almost reached the end of the class here.
}
