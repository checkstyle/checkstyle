/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;




/**
 * Contains examples of using comments at the end of the block.
 */
public class InputCommentsIndentationCommentIsAtTheEndOfBlockOne {

    public void foo1() {
        foo2();
        // OOOO: missing functionality
    }

    public void foo2() {
        foo3();
                         //violation '.* incorrect .* level 25, expected is 8,.*same.*as line 24.'
    }

    public void foo3() {
        foo2();
        // refreshDisplay();
    }

    public void foo4() {
        foooooooooooooooooooooooooooooooooooooooooo();
        // ^-- some hint
    }

    public void foooooooooooooooooooooooooooooooooooooooooo() { }
     // violation below '.* incorrect .* level 5, expected is 4, .* same .* as line 42.'
     /////////////////////////////// (a single-line border to separate a group of methods)

    public void foo7() {
        int a = 0;
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 43.'
    }

    /////////////////////////////// (a single-line border to separate a group of methods)

    public void foo8() {}

    public class TestClass {
        public void test() {
            int a = 0;
               //violation '.* incorrect .* level 15, expected is 12, .* same .* as line 53.'
        }
          //violation '.* incorrect .* level 10, expected is 8, .* same .* as line 52.'
    }

    public void foo9() {
        this.foo1();
             //violation '.* incorrect .* level 13, expected is 8, .* same .* as line 60.'
    }

    //    public void foo10() {
    //
    //    }

    public void foo11() {
        String
                .valueOf(new Integer(0))
                .trim()
                .length();
        // comment
    }

    public void foo12() {
        String
            .valueOf(new Integer(0))
            .trim()
            .length();
                  //violation '.* incorrect .* level 18, expected is 8, .* same .* as line 77.'
    }

    public void foo13() {
        String.valueOf(new Integer(0))
                .trim()
                .length();
        // comment
    }

    public void foo14() {
        String.valueOf(new Integer(0))
                .trim()
                .length();
                               //violation '.* incorrect .* level 31, expected is 8,.*as line 92.'
    }

    public void foo15() {
        String
                .valueOf(new Integer(0));
        // comment
    }

    public void foo16() {
        String
            .valueOf(new Integer(0));
                     //violation '.* incorrect .* level 21, expected is 8,.* same .* as line 105.'
    }

    public void foo17() {
        String
                .valueOf(new Integer(0))
                .trim()
                // comment
                .length();
    }
}
