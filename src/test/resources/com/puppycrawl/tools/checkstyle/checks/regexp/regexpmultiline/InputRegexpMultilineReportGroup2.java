/*
RegexpMultiline
format = xyz
message = Illegal literal found
ignoreCase = (default)false
minimum = (default)0
maximum = 1
matchAcrossLines = (default)false
reportGroup = -1
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

public class InputRegexpMultilineReportGroup2
{
    void method() {
// xyz  // violation
    }
}
