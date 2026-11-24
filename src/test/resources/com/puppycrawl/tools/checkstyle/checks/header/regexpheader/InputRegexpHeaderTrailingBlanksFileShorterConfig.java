/*
com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck
headerFiles = (file)InputRegexpHeaderTrailingBlanksFileShorter.header
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testHeaderWithTrailingBlanksFailsWhenFileIsShorter.
// The target file has fewer lines than the header, so the check reports
// MSG_HEADER_MISSING on line 1.
public class InputRegexpHeaderTrailingBlanksFileShorterConfig {
}
