/*
Header
headerFile = (file)InputHeaderjava.header
charset = (default)null
fileExtensions = (default)""
header = (default)null
ignoreLines = 4, 2, 3


*/

package com.puppycrawl.tools.checkstyle.checks.header.header;

// Config-only sidecar for testIgnoreLinesSorted. The actual target lives in
// InputHeaderjava3.header in the same directory and must stay untouched:
// HeaderCheck compares the target's lines to the configured header, so an
// inline config comment on the target would become part of the header being
// validated. ignoreLines is passed out of numeric order on purpose to
// exercise the sorting path.
public class InputHeaderjava3IgnoreLinesSortedConfig {
}
