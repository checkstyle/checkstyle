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

    // violation 2 lines below 'Line is smaller than 80 characters (found 25).'
    /**
     * This line is short
     * but could fit more text from the next line.
     */
    public void shortLineWithFollowup() { }

    // violation 2 lines below 'Line is smaller than 80 characters (found 20).'
    /**
     * Another short
     * example that demonstrates the too short case.
     */
    public void anotherShortLine() { }

    // violation 2 lines below 'Line is smaller than 80 characters (found 13).'
    /**
     * Brief.
     * And then more text that could have been on the first line.
     */
    public void briefWithMore() { }

    // violation 2 lines below 'Line is smaller than 80 characters (found 44).'
    /**
     * This is a simple short line that ends
     * with another short continuation.
     */
    public void multipleShortLines() { }

    // violation 2 lines below 'Line is smaller than 80 characters (found 12).'
    /**
     * Short
     * description that could easily fit on one line together.
     */
    public void veryShort() { }


    // violation 3 lines below 'Line is smaller than 80 characters (found 19).'
    // violation 3 lines below 'Line is smaller than 80 characters (found 34).'
    /**
     * @param value
     * This is a short description
     * that spans multiple lines unnecessarily.
     */
    public void paramTooShort(int value) { }

    // violation 3 lines below 'Line is smaller than 80 characters (found 14).'
    // violation 3 lines below 'Line is smaller than 80 characters (found 22).'
    /**
     * @return
     * Something short
     * when it could fit on the previous line. 
     */
    public int returnTooShort() {
        return 0;
    }
}
