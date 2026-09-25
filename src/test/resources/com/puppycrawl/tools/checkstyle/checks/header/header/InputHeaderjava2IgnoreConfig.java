/*
Header
headerFile = (file)InputHeaderjava.header
charset = (default)null
fileExtensions = (default)""
header = (default)null
ignoreLines = 2


*/

package com.puppycrawl.tools.checkstyle.checks.header.header;

// Config-only sidecar for testIgnore. The actual target lives in
// InputHeaderjava2.header in the same directory and must stay untouched:
// an inline config header on the target would collide with what
// HeaderCheck compares against the configured header, and the test
// intentionally uses a .header target to exercise mismatch on line 2.
public class InputHeaderjava2IgnoreConfig {
}
