/*
NewlineAtEndOfFile
lineSeparator = (default)LF_CR_CRLF
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.newlineatendoffile;

// Config-only sidecar for testFileWithEmptyLineOnlyWithLfCrCrlf. The actual
// target lives in InputNewlineAtEndOfFileNewlineAtEndLf.txt in the same
// directory and must stay a single LF byte so the "file with only an empty
// line" case is preserved for the check under the default LF_CR_CRLF
// lineSeparator.
public class InputNewlineAtEndOfFileNewlineAtEndLfConfig {
}
