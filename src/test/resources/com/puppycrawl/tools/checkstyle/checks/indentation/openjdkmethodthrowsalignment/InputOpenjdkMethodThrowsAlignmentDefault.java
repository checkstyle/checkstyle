/*
OpenjdkMethodThrowsAlignment

*/

package com.puppycrawl.tools.checkstyle.checks.indentation.openjdkmethodthrowsalignment;

public class InputOpenjdkMethodThrowsAlignmentDefault {

    public void noThrows(int first,
                         int second) {
    }

    public void singleLine(int first, int second) throws Exception {
    }
    // violation 2 lines below 'The throws clause should be on a new line.*'
    public void sameLine(int first,
                         int second) throws Exception {
    }

    public void declarationRelative(int first,
                                    int second)
            throws Exception {
    }

    public void previousLineRelative(int first,
            int second)
                    throws Exception {
    }

    int alignedWithPrevious(int first,
            int second)
            throws Exception { // violation 'The throws clause should be indented.*'
        return 0;
    }

    @Deprecated
    public void arbitraryIndentation(int first,
                                     int second)
                throws Exception { // violation 'The throws clause should be indented.*'
    }

    public void noThrowsSingleLine() {
    }

}
