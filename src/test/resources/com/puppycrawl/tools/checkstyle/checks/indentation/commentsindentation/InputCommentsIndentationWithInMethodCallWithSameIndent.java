package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

/**
 * Config: default
 */
public class InputCommentsIndentationWithInMethodCallWithSameIndent {
    String s1 = "ONE", s2 = "TWO", s3 = "THREE";
    private final boolean isEqualsOne = equals(
        // Some comment here
        s1,
        s2
        // ok
    );

    private final boolean isEqualsTwo = equals(
        // Some comment here
        s1,
        s2
      // violation
    );

    private final boolean isEqualsThree = equals(
        // Some comment here
        s1,
        s2
          // violation
    );

    private boolean equals(String s1, String s2) {
        return s1.equals(s2);
    }
}
