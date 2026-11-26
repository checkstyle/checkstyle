/*
RegexpHeader
headerFile = (default)null
charset = (default)null
fileExtensions = (default)""
header = \\\/\\\/ Nth Line of Header\\n\\\/\\\/ Nth Line of Header\\n
multiLines = 1


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testNonMatchingRegexpHeader. The actual target
// lives in InputRegexpHeaderNonMatchingTarget.java in the same directory
// and must stay untouched: embedding this config block in the target
// itself would be part of what RegexpHeaderCheck compares against the
// configured header patterns.
public class InputRegexpHeaderNonMatchingTargetConfig {
}
