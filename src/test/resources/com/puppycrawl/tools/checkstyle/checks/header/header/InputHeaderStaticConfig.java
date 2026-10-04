/*
Header
headerFile = (file)InputHeaderjava.header
charset = (default)null
fileExtensions = (default)""
header = (default)null
ignoreLines = (default)


*/

package com.puppycrawl.tools.checkstyle.checks.header.header;

// Config-only sidecar for testStaticHeader. The actual target lives in
// InputHeader.java in the same directory and has only a single code line,
// so the configured 18-line header pattern is longer than the file and the
// check reports MSG_MISSING on line 1.
public class InputHeaderStaticConfig {
}
