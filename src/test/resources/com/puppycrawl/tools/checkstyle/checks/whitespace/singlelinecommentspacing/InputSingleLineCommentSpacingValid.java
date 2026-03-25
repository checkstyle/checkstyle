package com.puppycrawl.tools.checkstyle.checks.whitespace.singlelinecommentspacing;

public class InputSingleLineCommentSpacingValid {

    void method() {
        int value = 1; // ok
        int slashSeparator = 2; //////
        int oddCase = 3; ///odd case
        int bareComment = 4; //

        // comment-only line
        ///
        //////
    }
}
