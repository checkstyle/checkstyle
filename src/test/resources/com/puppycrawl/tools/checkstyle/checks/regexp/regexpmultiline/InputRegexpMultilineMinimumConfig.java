/*
RegexpMultiline
format = \\r
message = (default)(null)
ignoreCase = (default)false
minimum = 5
maximum = (default)0
matchAcrossLines = (default)false
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

// Config-only sidecar for testMinimum. The actual target lives in
// InputRegexpMultilineMinimum.java in the same directory and must stay
// a zero-byte file so the "minimum occurrences not reached" case is
// what is being tested.
public class InputRegexpMultilineMinimumConfig {
}
