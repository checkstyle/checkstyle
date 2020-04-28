package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

/** Config: default */
public class InputJavadocTagContinuationIndentationDescription {
    /**
     * General Description here.
     *
     * @param s
     * Description 1. // violation
     * Description 2. // violation
     * Description 3. // violation
     *                         Description 4 with extra indentation.
     *
     *
     */
    public void testWithInvalidIndentation(String s) {}

    /**
     * General Description here.
     *
     * @param a
     *     Description 1
     * @param b
     *     Description 2
     */
    public void testWithValidIndentation(int a, int b) {}

    /** This is an inline description */
    public void testWithInlineDescription() {}

    /**
     * General Description.
     * @param x
       Description 1.
     */
    public void testWithMissingAsterisk(int x) {}

    /**
     * @param s
     *Description with missing space.
     * @param s2
     *****Description with multiple asterisk and missing space
     ***** Description with multiple asterisk and missing space
     */
    public void testOtherCases(String s, String s2) {}
}
