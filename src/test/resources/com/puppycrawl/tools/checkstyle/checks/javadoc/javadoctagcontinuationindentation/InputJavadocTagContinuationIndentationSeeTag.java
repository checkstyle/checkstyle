/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

/**
 *
 * @see reference <a href="https://checkstyle.org/">
 *   Checks the indentation of the continuation lines in block tags.</a>
 *
 * @see <a href="https://checkstyle.org/checks/javadoc/javadoctagcontinuationindentation.html">
 *   Checks the indentation of the continuation lines in block tags.</a>
 */
public class InputJavadocTagContinuationIndentationSeeTag {
    private static final int ValueZero = 0;
    // violation 7 lines above 'Line continuation have .* expected level should be 4'
    // violation 5 lines above 'Line continuation have .* expected level should be 4'

    private static final int ValueOne = 1;

   /**
    *
    *
    * @see reference <a href="https://checkstyle.org/">
    *   Checks the indentation of the continuation lines in block tags.</a>
    *
    * @see <a href="https://checkstyle.org/checks/javadoc/javadoctagcontinuationindentation.html">
    *     Checks the indentation of the continuation lines in block tags.</a>
    */
    public void testMethod1() {
        // violation 6 lines above 'Line continuation have .* expected level should be 4'
    }

    /**
     * @see
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDCDBGG">
     *     Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public void testMethod2() {

    }
}
