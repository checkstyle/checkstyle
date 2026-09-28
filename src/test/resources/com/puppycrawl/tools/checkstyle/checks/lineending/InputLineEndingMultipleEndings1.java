/*
LineEnding
lineEnding = crlf
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.lineending; // violation 'CRLF, but LF is detected.'
// violation 'CRLF, but CR is detected.'public class InputLineEndingMultipleEndings1 {
    public void method() { // violation 'CRLF, but CR is detected.'        int a = 1; // violation 'CRLF, but LF is detected.'
    } // violation 'CRLF, but CR is detected.'}
