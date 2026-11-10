/*
com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck
headerFiles = (file)InputRegexpHeader.header
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testMismatchInMiddleOfHeader. The target's
// second content line does not match the second header pattern line,
// so the check reports MSG_HEADER_MISMATCH on line 2.
public class InputRegexpHeaderMismatchInMiddleConfig {
}
