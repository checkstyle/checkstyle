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

    // violation 2 lines below 'Line under-utilized (25/80). Words from below could be moved up'
    /**
     * This line is short
     * but could fit more text from the next line.
     */
    public void shortLineWithFollowup() { }

    /**
     * This line is short but could fit more text from the next line.
     */
    public void correctedShortLineWithFollowup() { }

    // violation 2 lines below 'Line under-utilized (20/80). Words from below could be moved up'
    /**
     * Another short
     * example that demonstrates the too short case.
     */
    public void anotherShortLine() { }

    /**
     * Another short example that demonstrates the too short case.
     */
    public void corrrectedAnotherShortLine() { }

    // violation 2 lines below 'Line under-utilized (13/80). Words from below could be moved up'
    /**
     * Brief.
     * And then more text that could have been on the first line.
     */
    public void briefWithMore() { }

    /**
     * Brief. And then more text that could have been on the first line.
     */
    public void correctedBriefWithMore() { }

    // violation 2 lines below 'Line under-utilized (44/80). Words from below could be moved up'
    /**
     * This is a simple short line that ends
     * with another short continuation.
     */
    public void multipleShortLines() { }

    /**
     * This is a simple short line that ends with another short continuation.
     */
    public void correctedMultipleShortLines() { }

    // violation 2 lines below 'Line under-utilized (12/80). Words from below could be moved up'
    /**
     * Short
     * description that could easily fit on one line together.
     */
    public void veryShort() { }

    /**
     * Short description that could easily fit on one line together.
     */
    public void correctedVeryShort() { }


    // violation 3 lines below 'Line under-utilized (34/80). Words from below could be moved up'
    /**
     * @param value
     * This is a short description
     * that spans multiple lines unnecessarily.
     */
    public void paramTooShort(int value) { }

    /**
     * @param value
     * This is a short description that spans multiple lines unnecessarily.
     */
    public void correctedParamTooShort(int value) { }

    // violation 3 lines below 'Line under-utilized (22/80). Words from below could be moved up'
    /**
     * @return
     * Something short
     * when it could fit on the previous line.
     */
    public int returnTooShort() {
        return 0;
    }

    /**
     * @return
     * Something short when it could fit on the previous line.
     */
    public int correctedReturnTooShort() {
        return 0;
    }
}
