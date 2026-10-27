/*
TrailingComment
format = (default)^[\\s});]*$
legalComment = (default)(null)


*/

package org.checkstyle.suppressionxpathfilter.trailingcomment;

public class InputXpathTrailingCommentInsideMethod {
    void method() {
        // violation below 'Don't use trailing comments.'
        int x = 1; // warn
    }
}
