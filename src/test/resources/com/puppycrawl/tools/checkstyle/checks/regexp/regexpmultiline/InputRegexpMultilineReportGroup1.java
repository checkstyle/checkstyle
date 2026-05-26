/*
RegexpMultiline
format = Start(?s:.)*(End)
message = Illegal multiline match found
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
matchAcrossLines = true
reportGroup = 1
fileExtensions = (default)""

*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

public class InputRegexpMultilineReportGroup1 {
    void method() {
// Start
// End   // violation
    }
}
