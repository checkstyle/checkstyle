/*
LineEnding
lineEnding = cr
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.lineending;

// Config-only sidecar for testInputLineEndingMultipleEndings3. The actual
// target lives in InputLineEndingMultipleEndings3.java in the same
// directory and preserves a mixed CR/LF/CRLF byte shape so LineEndingCheck
// under lineEnding = cr reports every non-cr line ending.
public class InputLineEndingMultipleEndings3Config {
}
