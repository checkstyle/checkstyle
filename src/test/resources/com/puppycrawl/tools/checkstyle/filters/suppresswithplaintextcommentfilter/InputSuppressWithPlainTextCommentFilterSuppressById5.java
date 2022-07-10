/*
SuppressWithPlainTextCommentFilter
commentFormat = CSDEFAULT (\\w+) \\(\\w+\\)
offCommentFormat = CSOFF (\\w+) \\(\\w+\\)
onCommentFormat = CSON (\\w+)
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = $1
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
id = (null)
eachLine = true
fileExtensions = (default)


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class InputSuppressWithPlainTextCommentFilterSuppressById5 { // violation 'illegal pattern'

    //CSOFF ignore (reason)
    private int A1; // filtered violation 'illegal pattern'

    // @cs-: ignore (reason)
	private static final int a1 = 5; // filtered violation
    // violation above
    int a2 = 100; // filtered violation 'illegal pattern'
    //CSON ignore

    private long a3 = 1; // violation 'illegal pattern'

    // filtered violation below
	private static final long a4 = 1; // CSDEFAULT ignore (reason)
    // violation above
}
