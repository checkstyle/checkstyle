/*
RegexpMultiline
format = (// CONTEXT\\s*)(System\\.out\\.print\\()
message = (default)(null)
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
matchAcrossLines = true
reportGroup = 2
fileExtensions = (default)""

*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

public class InputRegexpMultilineReportGroup {
    void method() {
        // CONTEXT
        System.out.print("x"); // violation 'Line matches the illegal pattern'
    }
}
