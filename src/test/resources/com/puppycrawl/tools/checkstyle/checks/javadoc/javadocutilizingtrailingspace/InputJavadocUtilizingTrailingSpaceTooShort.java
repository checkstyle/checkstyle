/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for lines that break prematurely (too short).
 */
public class InputJavadocUtilizingTrailingSpaceTooShort {

    // violation 2 below 'Line is smaller than'
    /**
     * This line is short
     * but could fit more text from the next line.
     */
    public void shortLineWithFollowup() { }

    // violation 2 below 'Line is smaller than'
    /**
     * Another short
     * example that demonstrates the too short case.
     */
    public void anotherShortLine() { }

    // violation 2 below 'Line is smaller than'
    /**
     * Brief.
     * And then more text that could have been on the first line.
     */
    public void briefWithMore() { }

    // violation 2 below 'Line is smaller than'
    /**
     * This is a simple short line that ends
     * with another short continuation.
     */
    public void multipleShortLines() { }

    // violation 2 below 'Line is smaller than'
    /**
     * Short
     * description that could easily fit on one line together.
     */
    public void veryShort() { }


    // violation 3 below 'Line is smaller than'
    // violation 4 below 'Line is smaller than'
    /**
     * @param value
     * This is a short description
     * that spans multiple lines unnecessarily.
     */
    public void paramTooShort(int value) { }

    // violation 3 below 'Line is smaller than'
    // violation 4 below 'Line is smaller than'
    /**
     * @return
     * Something short
     * when it could fit on the previous line. 
     */
    public int returnTooShort() {
        return 0;
    }
}
