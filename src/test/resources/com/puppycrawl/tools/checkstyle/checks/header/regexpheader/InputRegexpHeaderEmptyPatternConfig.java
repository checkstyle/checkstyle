/*
com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck
headerFiles = (file)InputRegexpHeaderEmptyPattern.header
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testEmptyPatternMatch. The header fixture uses
// the ^$ regex on its middle line to match the blank middle line in the
// target, so the check reports nothing.
public class InputRegexpHeaderEmptyPatternConfig {
}
