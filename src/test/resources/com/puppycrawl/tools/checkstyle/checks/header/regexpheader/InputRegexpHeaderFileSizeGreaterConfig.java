/*
com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck
headerFiles = (file)InputRegexpHeaderFileSizeGreater.header
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testFileSizeGreaterThanHeaderPatternSize. The
// target has five content lines matching the first three header pattern
// lines, so the check reports nothing even though the file is longer than
// the header.
public class InputRegexpHeaderFileSizeGreaterConfig {
}
