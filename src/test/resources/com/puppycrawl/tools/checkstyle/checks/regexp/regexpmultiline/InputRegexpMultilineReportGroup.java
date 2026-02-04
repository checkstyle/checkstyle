/*
RegexpMultiline
format = (CONTEXT\\s*\\n\\s*)(System\\.out\\.print\\()
message = Console print after specific context
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
matchAcrossLines = true
reportGroup = 2
fileExtensions = (default)""

*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

class InputRegexpMultilineReportGroup {

    void test() {
        // CONTEXT
        System.out.print("Should be reported here"); // violation
    }

}
