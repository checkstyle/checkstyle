/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN

*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;
/**
 * Contains examples of using comments at the end of the block.
 */
public class InputCommentsIndentationCommentIsAtTheEndOfBlockSix {

    interface Bar1 {
        interface NestedBar { }

// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 544.'
    }
    static class Bar2 {
        enum Foo {
            A;
        }

        // violation '.* incorrect .* level 4, expected is 8, .* same .* as line 550.'
    }
    static class Bar3 {
        @interface Foo { }
        // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 558.'
    }

    void foo74() {
        getClass(); // comment
// comment
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 563.'
    }

    void foo75() {
        getClass();
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 569.'
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
//         violation '.* incorrect .* level 0, expected is 8, .* same .* as line 582.'
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
        // violation '.* incorrect .* level 12, expected is 8, .* same .* as line 595.'
    }
    void foo79() {
        /* violation */
        /* violation */new Object().toString();
        // ok
    }

    // We almost reached the end of the class here.
}
