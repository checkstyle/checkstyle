/*
SuppressWithPlainTextCommentFilter
commentFormat = (default)SUPPRESS CHECKSTYLE (\\w+)
offCommentFormat = (default)// CHECKSTYLE:OFF
onCommentFormat = (default)// CHECKSTYLE:ON
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = -2

com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck
eachLine = true
fileExtensions = (default)


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class InputSuppressWithPlainTextCommentFilterInfluenceFormatNegative2Lines {
	int thereIsTabBeforeMe7 = 5; // violation 'contains a tab'
	int thereIsTabBeforeMe16 = 5; // filtered violation 'contains a tab'
	int thereIsTabBeforeMe5 = 5; // filtered violation 'contains a tab'
    // SUPPRESS CHECKSTYLE FileTabCharacterCheck
	int thereIsTabBeforeMe1 = 5; // violation 'contains a tab'
	int thereIsTabBeforeMe2 = 5; // violation 'contains a tab'
	int thereIsTabBeforeMe3 = 5; // violation 'contains a tab'

	int thereIsTabBeforeMe12 = 5; // violation 'contains a tab'
	int thereIsTabBeforeMe13 = 5; // filtered violation 'contains a tab'
	int thereIsTabBeforeMe14 = 5; // filtered violation 'contains a tab'
	int thereIsTabBeforeMe11 = 5; // SUPPRESS CHECKSTYLE FileTabCharacterCheck
    // filtered violation above 'contains a tab'
	int thereIsTabBeforeMe15 = 5; // violation 'contains a tab'
}
