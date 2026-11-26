/*
RegexpHeader
headerFile = (file)InputRegexpHeader.invalid.header
charset = (default)null
fileExtensions = (default)""
header = (default)null
multiLines = (default)


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testHeaderWithInvalidRegexp. The target
// lives in InputRegexpHeaderInvalidRegexpTarget.java in the same
// directory. The configured headerFile contains a malformed regex
// at line 3 that triggers an IllegalArgumentException during check
// initialization, so no inline markers are expected on the target.
public class InputRegexpHeaderInvalidRegexpConfig {
}
