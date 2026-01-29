/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for block tag headers on their own lines (tag name only).
 */
public class InputJavadocUtilizingTrailingSpaceTagHeaderOnly {

    // violation 4 lines below 'Line is smaller than 80 characters (found 13).'
    /**
     * Description of method.
     *
     * @param
     *     value the input value with indented description
     */
    public void paramTagHeaderOnly(int value) { }

    // violation 2 lines below 'Line is smaller than 80 characters (found 14).'
    /**
     * @return
     *     the result value with its description on next line
     */
    public int returnTagHeaderOnly() {
        return 0;
    }

    // violation 2 lines below 'Line is smaller than 80 characters (found 14).'
    /**
     * @throws
     *     Exception when an error occurs during execution
     */
    public void throwsTagHeaderOnly() throws Exception { }

    /**
     * @param
     */
    public void paramNoDescription(int value) { }

    /**
     * @return
     */
    public int returnNoDescription() {
        return 0;
    }

    // violation 2 lines below 'Line is smaller than 80 characters (found 11).'
    /**
     * @see
     *     Object for base class documentation reference
     */
    public void seeTagHeaderOnly() { }

    // violation 6 lines below 'Line is smaller than 80 characters (found 13).'
    // violation 7 lines below 'Line is smaller than 80 characters (found 13).'
    // violation 8 lines below 'Line is smaller than 80 characters (found 14).'
    /**
     * Multi-param with headers:
     *
     * @param
     *     first the first parameter value
     * @param
     *     second the second parameter value
     * @return
     *     the combined result
     */
    public int multipleTagHeaders(int first, int second) {
        return first + second;
    }

    // violation 2 lines below 'Line is smaller than 80 characters (found 18).'
    /**
     * @deprecated
     *     This method is deprecated, use the newer version instead.
     */
    @Deprecated
    public void deprecatedWithHeader() { }
}
