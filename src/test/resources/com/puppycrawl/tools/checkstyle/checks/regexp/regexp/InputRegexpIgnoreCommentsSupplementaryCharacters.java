/*
Regexp
format = FIXME
message = (default)null
illegalPattern = true
duplicateLimit = (default)0
errorLimit = (default)100
ignoreComments = true


*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

public class InputRegexpIgnoreCommentsSupplementaryCharacters {
    String a = "x"; int aFIXME/* comment */; // violation 'illegal pattern 'FIXME''
    String b = "😀"; int bFIXME/* comment */; // violation 'illegal pattern 'FIXME''
    String c = "😀😀"; int cFIXME /* comment */; // violation 'illegal pattern 'FIXME''
    String d = "😀"; /* FIXME */
}
