package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

/**
 * Config: default
 */
public class InputCommentsIndentationWithInMethodCallWithSameIndent {
    String s1 = "ONE", s2 = "TWO", s3 = "THREE";
    private final boolean myList = someMethod(
        // Some comment here
        s1,
        s2
        // ok
    );

    private boolean someMethod(String s1, String s2) {
        return s1.equals(s2);
    }
}
