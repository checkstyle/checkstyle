/*
RegexpMultiline
format = (foo)?(ba[r])
message = (default)(null)
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
matchAcrossLines = (default)false
reportGroup = 1
fileExtensions = (default)""

*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

public class InputRegexpMultilineReportGroupNotParticipating {
    String s = "bar"; // violation 'Line matches the illegal pattern'
}
