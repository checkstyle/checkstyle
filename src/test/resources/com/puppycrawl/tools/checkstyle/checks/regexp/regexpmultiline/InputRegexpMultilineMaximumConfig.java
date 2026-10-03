/*
RegexpMultiline
format = \\r
message = (default)(null)
ignoreCase = (default)false
minimum = (default)0
maximum = 1
matchAcrossLines = (default)false
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

// Config-only sidecar for testMaximum. The actual target lives in
// InputRegexpMultilineMaximum.java in the same directory and preserves
// a mixed CR/LF/CRLF byte shape so RegexpMultiline with format = \r and
// maximum = 1 reports the second CR occurrence.
public class InputRegexpMultilineMaximumConfig {
}
