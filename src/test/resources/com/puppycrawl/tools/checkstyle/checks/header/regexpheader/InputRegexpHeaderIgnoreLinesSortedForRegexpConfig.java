/*
RegexpHeader
headerFile = (file)InputRegexpHeader5.header
charset = (default)null
fileExtensions = (default)""
header = (default)null
multiLines = 7, 5, 3


*/

package com.puppycrawl.tools.checkstyle.checks.header.regexpheader;

// Config-only sidecar for testIgnoreLinesSorted. The actual target lives
// in InputRegexpHeaderIgnoreLinesSorted.java in the same directory and
// must stay untouched: an inline config header on the target would be
// part of what RegexpHeaderCheck compares against the configured header
// pattern. multiLines is passed out of numeric order to exercise the
// sorting path.
public class InputRegexpHeaderIgnoreLinesSortedForRegexpConfig {
}
