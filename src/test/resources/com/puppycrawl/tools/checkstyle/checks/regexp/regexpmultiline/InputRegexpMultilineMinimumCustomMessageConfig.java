/*
RegexpMultiline
format = \\r
message = some message
ignoreCase = (default)false
minimum = 5
maximum = (default)0
matchAcrossLines = (default)false
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

// Config-only sidecar for testMinimumWithCustomMessage. The actual target
// lives in InputRegexpMultilineMinimumCustomMessage.java in the same
// directory and must stay a zero-byte file so the "minimum occurrences
// not reached" case is what is being tested, with a custom message.
public class InputRegexpMultilineMinimumCustomMessageConfig {
}
