/*
com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck
headerFiles = (file)InputRegexpHeaderNoMatchingHeaderFile.header
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testNoMatchingHeaderFile. The target has 2
// content lines that do not match the 2-line header pattern, so the
// check reports MSG_HEADER_MISMATCH on line 1 of the target.
public class InputRegexpHeaderNoMatchingHeaderFileConfig {
}
