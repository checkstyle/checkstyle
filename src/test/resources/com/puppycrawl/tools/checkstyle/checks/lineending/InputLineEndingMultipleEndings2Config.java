/*
LineEnding
lineEnding = (default)lf
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.lineending;

// Config-only sidecar for testInputLineEndingMultipleEndings2. The actual
// target lives in InputLineEndingMultipleEndings2.java in the same
// directory and preserves a mixed CR/LF/CRLF byte shape so LineEndingCheck
// under the default lineEnding = lf reports every non-lf line ending.
public class InputLineEndingMultipleEndings2Config {
}
