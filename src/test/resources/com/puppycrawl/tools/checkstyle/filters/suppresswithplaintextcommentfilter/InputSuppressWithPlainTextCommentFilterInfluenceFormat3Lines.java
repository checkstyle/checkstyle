/*
SuppressWithPlainTextCommentFilter
areaCommentFormat = (default)SUPPRESS CHECKSTYLE (\\w+)
offCommentFormat = (default)// CHECKSTYLE:OFF
onCommentFormat = (default)// CHECKSTYLE:ON
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = 3

com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck
eachLine = true
fileExtensions = (default)


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class InputSuppressWithPlainTextCommentFilterInfluenceFormat3Lines {
	int thereIsTabBeforeMe5 = 5; // violation 'contains a tab'
    // SUPPRESS CHECKSTYLE FileTabCharacterCheck
	int thereIsTabBeforeMe1 = 5; // filtered violation 'contains a tab'
	int thereIsTabBeforeMe2 = 5; // filtered violation 'contains a tab'
	int thereIsTabBeforeMe3 = 5; // filtered violation 'contains a tab'
	int thereIsTabBeforeMe4 = 5; // violation 'contains a tab'

    /* SUPPRESS CHECKSTYLE FileTabCharacterCheck */
	int thereIsTabBeforeMe7 = 5; // filtered violation 'contains a tab'
	int thereIsTabBeforeMe8 = 5; // filtered violation 'contains a tab'
	int thereIsTabBeforeMe9 = 5; // filtered violation 'contains a tab'
	int thereIsTabBeforeMe10 = 5; // violation 'contains a tab'

    // filtered violation below 'contains a tab'
	int thereIsTabBeforeMe11 = 5; // SUPPRESS CHECKSTYLE FileTabCharacterCheck
	int thereIsTabBeforeMe12 = 5; // filtered violation 'contains a tab'
	int thereIsTabBeforeMe13 = 5; // filtered violation 'contains a tab'
	int thereIsTabBeforeMe14 = 5; // filtered violation 'contains a tab'
	int thereIsTabBeforeMe15 = 5; // violation 'contains a tab'
}
