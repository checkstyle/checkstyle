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
     *   </a>
     */
    // violation 3 lines above 'Line continuation .* expected level should be 4'
    // violation 3 lines above 'Line continuation .* expected level should be 4'
    static class Test {}

    /**
     *
     *  <a href="https://checkstyle.org/checks/javadoc">
     *    Checks the indentation of the continuation lines
     *  </a>
     */
    static class Test1 {}

    /**
     * This method demonstrates inline tags, which should be skipped.
     * {@link java.util.List} {@code String} {@literal example}
     *
     * @see <a href="https://example.com">
     *     This should follow correct indentation.</a>
     */
    static class InlineTagTest {}

    /**
     * Another test case with inline tags:
     * This is a {@link java.util.Map} example.
     *
     * @see <a href="https://another-example.com">
     *     Another indentation check.</a>
     */
    static class InlineTagTest2 {}

    /**
     * Mixed case with inline and block tags.
     * {@linkplain java.util.Set} example
     *
     * @see <a href="https://checkstyle.org">
     *     JavadocTagContinuationIndentation test case</a>
     */
    static class MixedInlineBlockTest {}

}
