/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

public class InputJavadocTagContinuationIndentationCheck1 {

    // violation 6 lines below 'Line continuation .* expected level should be 4'
    // violation 8 lines below 'Line continuation .* expected level should be 4'
    // violation 8 lines below 'Line continuation .* expected level should be 4'
    /**
     *
     * @see <a href="https://checkstyle.org/checks/javadoc">
     *   JavadocTagContinuationIndentation: Checks the indentation.</a>
     *
     * @see
     * <a href="https://checkstyle.org/checks/javadoc">
     *   JavadocTagContinuationIndentation: Checks the indentation.</a>
     */
    void foo2() {}

    /*****************************************************************
     * Constructs a <code>MalformedURIException</code> with the
     * specified detail message.
     *
     * @param message the detail message.
     ******************************************************************/
    void openJdkBoxedJavadoc() {}
}
