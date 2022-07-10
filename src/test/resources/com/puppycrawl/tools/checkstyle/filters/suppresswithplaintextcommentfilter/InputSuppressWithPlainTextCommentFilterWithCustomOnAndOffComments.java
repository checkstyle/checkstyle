/*
SuppressWithPlainTextCommentFilter
commentFormat = cs (\\w+)
offCommentFormat = cs-off
onCommentFormat = cs-on
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = (default)0

com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck
eachLine = true
fileExtensions = (default)


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class InputSuppressWithPlainTextCommentFilterWithCustomOnAndOffComments {
    // cs-off
    //	has tab here
    // filtered violation above 'Line contains a tab character'

    // cs-on
    //	has tab here
    // violation above 'Line contains a tab character'

    /* cs-off **/ private int	b; // filtered violation 'Line contains a tab character'
    /* cs-on **/

    private 	int c; // violation 'Line contains a tab character'

    // filtered violation below 'Line contains a tab character'
	private int d; // cs FileTabCharacterCheck

    // filtered violation below 'Line contains a tab character'
	private int e;  /* cs FileTabCharacterCheck */
	/* cs FileTabCharacterCheck */ private int f;
    // filtered violation above 'Line contains a tab character'

    // violation below 'Line contains a tab character'
	private int g;
    // cs FileTabCharacterCheck

    // cs FileTabCharacterCheck
	private int h;
    // violation above 'Line contains a tab character'
}
