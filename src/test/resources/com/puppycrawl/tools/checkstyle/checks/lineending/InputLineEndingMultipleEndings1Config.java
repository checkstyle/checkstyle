/*
LineEnding
lineEnding = crlf
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.lineending;

// Config-only sidecar for testInputLineEndingMultipleEndings1. The actual
// target lives in InputLineEndingMultipleEndings1.java in the same
// directory and preserves a mixed CR/LF/CRLF byte shape so LineEndingCheck
// under lineEnding = crlf reports every non-crlf line ending.
public class InputLineEndingMultipleEndings1Config {
}
