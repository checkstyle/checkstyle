/*
SuppressWithPlainTextCommentFilter
commentFormat = (default)SUPPRESS CHECKSTYLE (\w+)
offCommentFormat = (default)// CHECKSTYLE:OFF
onCommentFormat = (default)// CHECKSTYLE:ON
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = (default)0

com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck
eachLine = true
fileExtensions = (default)


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class InputSuppressWithPlainTextCommentFilterWithDefaultCfg {
    // CHECKSTYLE:OFF
    //	has tab here
    // filtered violation above 'Line contains a tab character'

    // CHECKSTYLE:ON
    //	has tab here
    // violation above 'Line contains a tab character'

    // filtered violation below 'Line contains a tab character'
	private int a; // CHECKSTYLE:OFF
    // CHECKSTYLE:ON

    // filtered violation below 'Line contains a tab character'
	private int b; // SUPPRESS CHECKSTYLE FileTabCharacterCheck

    // filtered violation below 'Line contains a tab character'
	private int c;  /* SUPPRESS CHECKSTYLE FileTabCharacterCheck */
	/* SUPPRESS CHECKSTYLE FileTabCharacterCheck */ private int A3;
    // filtered violation above 'Line contains a tab character'

    // violation below 'Line contains a tab character'
	private int d;
    // SUPPRESS CHECKSTYLE FileTabCharacterCheck

    // SUPPRESS CHECKSTYLE FileTabCharacterCheck
	private int e;
    // violation above 'Line contains a tab character'
}
