/*
RegexpMultiline
format = // (Optional)?Required
message = Required pattern match
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
matchAcrossLines = (default)false
reportGroup = 1
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

public class InputRegexpMultilineReportGroup3 {
    void method() {
        // Required  // violation
    }
}
