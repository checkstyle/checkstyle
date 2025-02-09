/*
SuppressWithPlainTextCommentFilter
offCommentFormat = // CHECKSTYLE:OFF
onCommentFormat = // CHECKSTYLE:ON
checkFormat = (default).*
messageFormat = .*tab.*
idFormat = (default)(null)


com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck
id = ignore
format = .*[a-zA-Z][0-9].*
message = (default)
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
fileExtensions = (default)(null)


com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck
eachLine = true
fileExtensions = (default)


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class InputSuppressWithPlainTextCommentFilterCustomMessageFormat {

    // CHECKSTYLE:OFF
    private int A1; // violation 'illegal pattern'

	private static final int a1 = 5; // filtered violation 'contains a tab'
    // violation above 'illegal pattern'
    int a2 = 100; // violation 'illegal pattern'

    // CHECKSTYLE:ON
    private long a3 = 1; // violation 'illegal pattern'

}
