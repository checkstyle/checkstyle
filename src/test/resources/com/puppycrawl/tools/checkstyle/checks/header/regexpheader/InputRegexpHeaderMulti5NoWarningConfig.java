/*
com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck
headerFiles = (file)InputRegexpHeader4.header
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testNoWarningIfSingleLinedLeft. The actual
// target lives in InputRegexpHeaderMulti5.java in the same directory and
// must stay untouched: an inline config header on the target would be
// part of what MultiFileRegexpHeaderCheck compares against the configured
// header patterns.
public class InputRegexpHeaderMulti5NoWarningConfig {
}
