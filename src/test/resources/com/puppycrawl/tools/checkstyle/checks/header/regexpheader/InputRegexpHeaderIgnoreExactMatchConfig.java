/*
com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck
headerFiles = (file)InputRegexpHeader1.header
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testAllHeaderLinesMatchedExactly. The actual
// target lives in InputRegexpHeaderIgnore.java in the same directory and
// must stay untouched: an inline config header on the target would collide
// with what MultiFileRegexpHeaderCheck reads as the file's actual header.
public class InputRegexpHeaderIgnoreExactMatchConfig {
}
