/*
TrailingComment
format = (default)^[\s});]*$
legalComment = \\$NON-NLS


*/
package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

public class InputTrailingCommentWithNoPrecedingWhitespace {
    public static void main(String[] args) {
        System.out.println("foo"); //$NON-NLS-1$
    }                              // ok ^
}
