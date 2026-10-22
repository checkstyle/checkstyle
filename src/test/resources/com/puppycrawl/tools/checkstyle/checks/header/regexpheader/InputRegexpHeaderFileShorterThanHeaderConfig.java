/*
com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck
headerFiles = (file)InputRegexpHeaderFileShorterThanHeader.header
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testFileFailsWhenShorterThanHeader. The target
// has fewer lines than the header template so the check reports
// MSG_HEADER_MISSING on line 1 of the target.
public class InputRegexpHeaderFileShorterThanHeaderConfig {
}
