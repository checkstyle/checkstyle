/*
Header
headerFile = (file)InputHeaderjava.header
charset = (default)null
fileExtensions = (default)""
header = (default)null
ignoreLines = (default)


*/

package com.puppycrawl.tools.checkstyle.checks.header.header;

// Config-only sidecar for testNotMatch. The actual target is a copy of
// InputHeaderjava2.header living alongside this file; it intentionally
// has a //testvdfv prefix on line 2 that mismatches the configured
// header's line 2, so the check reports MSG_MISMATCH on line 2.
public class InputHeaderjava2NotMatchConfig {
}
