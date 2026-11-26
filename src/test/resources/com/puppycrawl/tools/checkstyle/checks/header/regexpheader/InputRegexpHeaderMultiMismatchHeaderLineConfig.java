/*
com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck
headerFiles = (file)InputRegexpHeader.header
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testMismatchInHeaderLine. The target lives
// in InputRegexpHeaderMultiMismatchHeaderLine.java in the same
// directory and stays untouched; embedding this config block in the
// target itself would be part of what MultiFileRegexpHeaderCheck
// compares against the configured header patterns.
public class InputRegexpHeaderMultiMismatchHeaderLineConfig {
}
