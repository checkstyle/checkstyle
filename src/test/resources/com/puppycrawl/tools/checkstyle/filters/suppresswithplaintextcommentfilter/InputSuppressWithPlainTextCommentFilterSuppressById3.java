/*
SuppressWithPlainTextCommentFilter
commentFormat = CSDEFAULT (\\w+) \\(\\w+\\)
offCommentFormat = CSOFF (\\w+) \\(\\w+\\)
onCommentFormat = CSON (\\w+)
checkFormat = FileTabCharacterCheck
messageFormat = (default)(null)
idFormat = foo
influenceFormat = (default)0


com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck
id = ignore
format = .*[a-zA-Z][0-9].*
message = (default)
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
fileExtensions = (default)all files


com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck
id = foo
eachLine = true
fileExtensions = (default)


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class InputSuppressWithPlainTextCommentFilterSuppressById3 { // violation 'illegal pattern'

    //CSOFF ignore (reason)
    private int A1; // violation 'illegal pattern'

    // @cs-: ignore (reason)
	private static final int a1 = 5; // filtered violation 'contains a tab'
    // violation above 'illegal pattern'
    int a2 = 100; // violation 'illegal pattern'
    //CSON ignore

    private long a3 = 1; // violation 'illegal pattern'

    // filtered violation below 'contains a tab'
	private static final long a4 = 1; // CSDEFAULT ignore (reason)
    // violation above 'illegal pattern'
}
