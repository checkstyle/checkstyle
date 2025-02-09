/*
SuppressWithPlainTextCommentFilter
offCommentFormat = CSOFF (\\w+) \\(\\w+\\)
onCommentFormat = CSON (\\w+)
checkFormat = FileTabCharacterCheck
messageFormat = (default)(null)
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
id = foo
eachLine = true
fileExtensions = (default)


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class InputSuppressWithPlainTextCommentFilterSuppressById {

    //CSOFF ignore (reason)
    private int A1; // violation 'illegal pattern'

    // @cs-: ignore (reason)
	private static final int a1 = 5; // filtered violation 'contains a tab'
    // violation above 'illegal pattern'
    int a2 = 100; // violation 'illegal pattern'
    //CSON ignore

    private long a3 = 1; // violation 'illegal pattern'

}
