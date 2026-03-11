/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for properly formatted Javadocs with no violations.
 */
public class InputJavadocUtilizingTrailingSpaceNoViolations {

    /**
     * This is a properly formatted Javadoc comment that utilizes the available
     * horizontal space efficiently without exceeding the line limit.
     */
    public void properlyFormatted() { }

    /**
     * Short but complete description that fills the line appropriately.
     */
    public void singleLineProper() { }

    /**
     * This description is well-formatted and uses the space efficiently. Each
     * line is filled close to the limit before wrapping to the next line.
     */
    public void multiLineProper() { }

    /**
     * @param value the input value to be processed by the method appropriately
     */
    public void paramProper(int value) { }

    /**
     * @return the result of the computation performed by this method correctly
     */
    public int returnProper() {
        return 0;
    }

    /**
     * @param first the first parameter with a description that fits properly
     * @param second the second parameter with another fitting description here
     * @return the combined result of processing both input parameters given
     */
    public int multipleParamsProper(int first, int second) {
        return first + second;
    }
}
