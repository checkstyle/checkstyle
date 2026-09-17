/*
OpenjdkMethodThrowsAlignment


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.openjdkmethodthrowsalignment;

public class InputOpenjdkMethodThrowsAlignment {
    void testMethod(String aString,
                    int bInt) throws InterruptedException {
        // violation above """The 'throws' clause should be on a new line when the
        // method declaration is wrapped."""
    }

    void testMethod1(String aString,
                     int bInt,
                     int cInt)
            throws InterruptedException {
    }

    void testMethod2(String aString,
                    int bInt,
                    int cInt)
                            throws InterruptedException {
    }

    void testMethod3(String aString,
                     int bInt)
                    throws InterruptedException {
        // violation above """The 'throws' clause should be indented 8 spaces relative to the
        // method declaration or the previous line and should not align with the previous line."""
    }

    void met(int a,
            int b)
            throws Exception {
        // violation above """The 'throws' clause should be indented 8 spaces relative to the
        // method declaration or the previous line and should not align with the previous line."""
    }

    void met2(int aInt,
        int bInt)
        throws InterruptedException {
        // violation above """The 'throws' clause should be indented 8 spaces relative to the
        // method declaration or the previous line and should not align with the previous line."""
    }

    void met3(int aInt,
              int bInt)
                throws InterruptedException {
        // violation above """The 'throws' clause should be indented 8 spaces relative to the
        // method declaration or the previous line and should not align with the previous line."""
    }

    void noThrowsWrapped(String aString,
                         int bInt) {
    }

    void singleLine(int aInt) throws InterruptedException {
    }

    InputOpenjdkMethodThrowsAlignment(String aString,
                                      int bInt) throws InterruptedException {
        // violation above """The 'throws' clause should be on a new line when
        // the method declaration is wrapped."""
    }

    InputOpenjdkMethodThrowsAlignment(String aString,
                                      int bInt,
                                      int cInt)
            throws InterruptedException {
    }

    InputOpenjdkMethodThrowsAlignment(int aInt,
                                      int bInt)
        throws InterruptedException {
        // violation above """The 'throws' clause should be indented 8 spaces relative to the
        // method declaration or the previous line and should not align with the previous line."""
    }

    void multipleExceptions(String aString,
                            int bInt)
            throws InterruptedException,
                   IllegalArgumentException {
    }
}
