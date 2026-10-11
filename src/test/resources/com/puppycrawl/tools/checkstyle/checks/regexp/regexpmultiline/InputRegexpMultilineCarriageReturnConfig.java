/*
RegexpMultiline
format = \\r
message = (default)(null)
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
matchAcrossLines = (default)false
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

// Config-only sidecar for testCarriageReturn. The actual target lives in
// InputRegexpMultilineCarriageReturn.java in the same directory and
// preserves a mixed CR/LF/CRLF byte shape so RegexpMultiline with
// format = \r and maximum = 0 reports both CR occurrences.
public class InputRegexpMultilineCarriageReturnConfig {
}
