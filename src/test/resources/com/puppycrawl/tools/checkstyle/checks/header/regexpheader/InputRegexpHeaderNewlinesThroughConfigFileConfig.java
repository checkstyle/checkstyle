/*
com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck
headerFiles = (file)(uri)InputRegexpHeaderNewLines.header
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testInlineRegexpHeaderConsecutiveNewlinesThroughConfigFile.
// The actual target lives in InputRegexpHeaderConsecutiveNewLines.java in
// the same directory and stays untouched. headerFiles is resolved as a
// uri so the check's error message contains the file: URI form that the
// expected message asserts.
public class InputRegexpHeaderNewlinesThroughConfigFileConfig {
}
