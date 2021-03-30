package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

/**
 * Config: default
 */
public class InputCommentsIndentationWithInMethodCallWithSameIndent {
    String s1 = "ONE", s2 = "TWO", s3 = "THREE";
    private final boolean myList = someMethod(
        // Some comment here
        s1,
        s2,
        s3
        // ok
    );

    private boolean someMethod(String s1, String s2, String s3) {
        return s1.equals(s2) && s1.equals(s3);
    }
}
