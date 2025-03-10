/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

/**
 *
 * @see reference <a href="https://checkstyle.org/checks/javadoc">
 *   JavadocTagContinuationIndentation: Checks the indentation of the continuation</a>
 *
 * @see <a href="https://checkstyle.org/checks/javadoc/javadoctagcontinuationindentation.html">
 *   JavadocTagContinuationIndentation: Checks the indentation of the continuation lines.</a>
 */
// violation 5 lines above 'Line continuation .* expected level should be 4'
// violation 3 lines above 'Line continuation .* expected level should be 4'

public class InputJavadocTagContinuationIndentationCheckHtml {

    /**
     *
     * @see reference <a
     *   href="https://checkstyle.org/checks/javadoc/javadoctagcontinuationindentation.html">
     *   JavadocTagContinuationIndentation: Checks the indentation of the continuation lines
     *   </a>  // violation not raised
     */
    // violation 3 lines above 'Line continuation .* expected level should be 4'
    // violation 3 lines above 'Line continuation .* expected level should be 4'
    static class Test {}

}
