/*
com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck
headerFiles = (file)(uri)InputRegexpHeader.header
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for MultiFileRegexpHeaderCheckTest.testRegexpHeaderUrl.
// The target lives in InputRegexpHeaderMultiRegexpHeaderUrl.java in
// the same directory and stays untouched; embedding this config block
// in the target itself would be part of what MultiFileRegexpHeaderCheck
// compares against the configured header patterns. headerFiles is
// resolved as a uri to exercise the URI-form code path.
public class InputRegexpHeaderMultiRegexpHeaderUrlConfig {
}
