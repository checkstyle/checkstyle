/*
RegexpHeader
headerFile = (file)InputRegexpHeader3.header
charset = (default)null
fileExtensions = (default)""
header = (default)null
multiLines = 1


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testNoHeaderMissingErrorInCaseHeaderSizeEqualToFileSize.
// The actual target lives in InputRegexpHeaderNoHeaderMissingError.txt in the same directory
// and must stay untouched: an inline config header on the target would be part of what
// RegexpHeaderCheck compares against the configured header patterns.
public class InputRegexpHeaderNoHeaderMissingErrorConfig {
}
