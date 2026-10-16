/*
TrailingComment
format = (default)^[\\s});]*$
legalComment = (default)(null)


*/

package org.checkstyle.suppressionxpathfilter.trailingcomment;

public class InputXpathTrailingCommentSingleLine {
    // violation below 'Don't use trailing comments.'
    int i; // don't use trailing comments :) // warn
}
