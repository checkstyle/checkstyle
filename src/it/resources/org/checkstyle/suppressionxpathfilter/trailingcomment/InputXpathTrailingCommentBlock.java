/*
TrailingComment
format = (default)^[\\s});]*$
legalComment = (default)(null)


*/

package org.checkstyle.suppressionxpathfilter.trailingcomment;

public class InputXpathTrailingCommentBlock {
    // violation below 'Don't use trailing comments.'
    int j; /* bad c-style comment. */  // warn
}
