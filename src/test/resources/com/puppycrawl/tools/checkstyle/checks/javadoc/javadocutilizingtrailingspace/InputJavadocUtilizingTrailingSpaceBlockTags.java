/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for Javadoc block tags (@param, @return, @throws, @see).
 */
public class InputJavadocUtilizingTrailingSpaceBlockTags {

    /**
     * Method with block tags.
     *
     * @param first the first parameter with a reasonable description here
     * @param second the second parameter that also has a description
     * @return the sum of both parameters provided to this method
     * @throws IllegalArgumentException if parameters are negative values
     */
    public int methodWithTags(int first, int second) {
        if (first < 0 || second < 0) {
            throw new IllegalArgumentException("Negative");
        }
        return first + second;
    }
    // violation 2 lines below 'Line is smaller than 80 characters (found 19).'
    /**
     * @param value
     *     indented description on the next line
     */
    public void tagWithIndentedValue(int value) { }

    /**
     * @param a short
     * @param b another short description for parameter b value
     */
    public void multipleParams(int a, int b) { }

    // violation 2 lines below 'Line is smaller than 80 characters (found 14).'
    /**
     * @return
     *     a value that is described on an indented line appropriately
     */
    public int returnIndented() {
        return 0;
    }

    /**
     * @throws RuntimeException when the runtime encounters an error during
     *     execution of this method
     */
    public void throwsMultiLine() { }

    /**
     * @see String for string handling information and documentation
     * @see Object for base object documentation and methods available
     */
    public void multipleSee() { }

    /**
     * @deprecated Use {@link #newMethod()} instead for better performance.
     */
    @Deprecated
    public void oldMethod() { }

    /**
     * New method.
     */
    public void newMethod() { }
}
