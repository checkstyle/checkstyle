/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;





/**
 * Contains examples of using comments at the end of the block.
 */
public class InputCommentsIndentationCommentIsAtTheEndOfBlockThree {

    public void foo30() {
        // comment
        int a = 5;
//violation '.* incorrect .* level 0, expected is 8, .* same .* as line 20.'
    }

    public void foo31() {
        String s = new String ("A"
                + "B"
                + "C");
        // comment
    }

    public void foo32() {
        String s = new String ("A"
            + "B"
            + "C");
            //violation '.* incorrect .* level 12, expected is 8, .* same .* as line 32.'
    }

    public void foo33() {
        // comment
        new InputCommentsIndentationCommentIsAtTheEndOfBlockTwo().foo22();

//        violation '.* incorrect .* level 0, expected is 8, .* same .* as line 40.'
    }

    public void foo34() throws Exception {
        throw new Exception("",
                new Exception()
        );
        // comment
    }

    public void foo35() throws Exception {
        throw new Exception("",
                new Exception()
        );
            //violation '.* incorrect .* level 12, expected is 8, .* same .* as line 53.'
    }

    public void foo36() throws Exception {
        throw new Exception("",
                new Exception()
        );
//      violation '.* incorrect .* level 0, expected is 8, .* same .* as line 60.'
    }

    public void foo37() throws Exception {
        throw new Exception("", new Exception());
        // comment
    }

    public void foo38() throws Exception {
        throw new Exception("", new Exception());
              //violation '.* incorrect .* level 14, expected is 8, .* same .* as line 72.'
    }

    public void foo39() throws Exception {
        throw new Exception("",
                new Exception());
         //violation '.* incorrect .* level 9, expected is 8, .* same .* as line 77.'
    }

    public void foo40() throws Exception {
        int a = 88;
        throw new Exception("", new Exception());
         //violation '.* incorrect .* level 9, expected is 8, .* same .* as line 84.'
    }

    public void foo41() throws Exception {
        int a = 88;
        throw new Exception("", new Exception());
        // comment
    }

    public void foo42() {
        int a = 5;
        if (a == 5) {
            int b;
            // comment
        } else if (a ==6) {

        }
    }
    public void foo43() {
        try {
            int a;
            // comment
        } catch (Exception e) {

        }
    }

    public void foo44() {
        int ar = 5;
        // comment
        ar = 6;
        // comment
    }

}
