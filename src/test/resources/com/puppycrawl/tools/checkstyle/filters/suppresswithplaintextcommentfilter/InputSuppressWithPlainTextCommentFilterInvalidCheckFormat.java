/*
SuppressWithPlainTextCommentFilter
offCommentFormat = // cs-off
onCommentFormat = // cs-on
checkFormat = e[l
messageFormat = (default)(null)
idFormat = (default)(null)

com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck
eachLine = true
fileExtensions = (default)(null)


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class InputSuppressWithPlainTextCommentFilterInvalidCheckFormat {
    // cs-off
    //	has tab here
    // filtered violation above 'Line contains a tab character'

    // cs-on
    //	has tab here
    // violation above 'Line contains a tab character'

    /* cs-off **/ private int	b; // filtered violation 'Line contains a tab character'
    /* cs-on **/

    private 	int c; // violation 'Line contains a tab character'
}
