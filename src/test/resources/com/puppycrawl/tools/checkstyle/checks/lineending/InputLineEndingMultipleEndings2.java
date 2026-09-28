/*
LineEnding
lineEnding = (default)lf
fileExtensions = (default)""


*/

package com.puppycrawl.tools.checkstyle.checks.lineending;
// violation 'LF, but CR is detected.'public class InputLineEndingMultipleEndings2 { // violation 'LF, but CRLF is detected.'
    public void method() { // violation 'LF, but CR is detected.'        int a = 1;
    } // violation 'LF, but CR is detected.'} // violation 'LF, but CRLF is detected.'
