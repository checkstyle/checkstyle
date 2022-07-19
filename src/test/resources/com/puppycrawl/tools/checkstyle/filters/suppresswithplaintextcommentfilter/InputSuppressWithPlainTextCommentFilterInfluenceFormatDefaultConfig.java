/*
SuppressWithPlainTextCommentFilter
areaCommentFormat = (default)SUPPRESS CHECKSTYLE (\\w+)
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

public class InputSuppressWithPlainTextCommentFilterInfluenceFormatDefaultConfig {
	int thereIsTabBeforeMe = 5; // violation 'contains a tab'

    // SUPPRESS CHECKSTYLE FileTabCharacterCheck
	int thereIsTabBeforeMe1 = 5; // violation 'contains a tab'

    // filtered violation below 'contains a tab'
	int thereIsTabBeforeMe2 = 5; // SUPPRESS CHECKSTYLE FileTabCharacterCheck

    // filtered violation below 'contains a tab'
	int thereIsTabBeforeMe3 = 5; /* SUPPRESS CHECKSTYLE FileTabCharacterCheck */

    // filtered violation below 'contains a tab'
	/* SUPPRESS CHECKSTYLE FileTabCharacterCheck */ int thereIsTabBeforeMe4 = 5;
}
