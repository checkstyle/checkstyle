/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationWithInMethodCallWithSameIndent {
    String s1 = "ONE", s2 = "TWO", s3 = "THREE";
    private final boolean isEqualsOne = equals(
        // Some comment here
        s1,
        s2
    );

    private final boolean isEqualsTwo = equals(
        // Some comment here
        s1,
        s2
      // violation '.* incorrect .* level 6, expected is 4, .* same .* as line 24.'
    );

    private final boolean isEqualsThree = equals(
        // Some comment here
        s1,
        s2
          // violation '.* incorrect .* level 10, expected is 4, .* same .* as line 31.'
    );

    private boolean equals(String s1, String s2) {
        return s1.equals(s2);
    }
}
