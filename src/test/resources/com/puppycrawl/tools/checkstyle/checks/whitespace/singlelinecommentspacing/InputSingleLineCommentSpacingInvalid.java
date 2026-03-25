package com.puppycrawl.tools.checkstyle.checks.whitespace.singlelinecommentspacing;

public class InputSingleLineCommentSpacingInvalid {

    void method() {
        int ok = 1; // ok

        //comment-only line
        ///
        //////

        int both = 1;//bad
        int afterOnly = 2; //bad
        int beforeOnly = 3;// bad
        int bareComment = 4;//
    }
}
