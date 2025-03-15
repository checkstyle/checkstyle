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
     * @see <a
     *   href="https://checkstyle.org/checks/javadoc/javadoctagcontinuationindentation.html">
     *   JavadocTagContinuationIndentation: Checks the indentation of the continuation lines
     *   </a>
     */
    // violation 3 lines above 'Line continuation .* expected level should be 4'
    // violation 3 lines above 'Line continuation .* expected level should be 4'
    static class Test {}

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
     * Converts the input string to uppercase.
     *
     * @param input the input string
     * @return the uppercase version of input. Refer
     *   <a href="https://example.com/string-utils">
     *   String Utilities Guide
     *   </a>
     */
    // violation 4 lines above 'Line continuation .* expected level should be 4'
    // violation 4 lines above 'Line continuation .* expected level should be 4'
    // violation 4 lines above 'Line continuation .* expected level should be 4'
    static String toUpperCase(String input) {
        return input.toUpperCase();
    }

    /**
     * Something.
     *
     * @param source <a href="https://example.com/data-source">
     * Data Source Documentation
     * </a>
     */
    // violation 3 lines above 'Line continuation .* expected level should be 4'
    // violation 3 lines above 'Line continuation .* expected level should be 4'
    static void readData(String source) {}

    /**
     * Something.
     *
     * @deprecated Use {@link #getUserInfo()} instead. More details
     *   <a href="https://example.com/api-changes">
     *   here
     *   </a>
     */
    // violation 4 lines above 'Line continuation .* expected level should be 4'
    // violation 4 lines above 'Line continuation .* expected level should be 4'
    // violation 4 lines above 'Line continuation .* expected level should be 4'
    @Deprecated
    static void getUserData() {}

    /**
     *
     * @see <a href="https://checkstyle.org/checks/javadoc/javadoctagcontinuationindentation.html">
     * JavadocTagContinuationIndentation: Checks the indentation of the continuation lines
     * </a>
     */
    // violation 3 lines above 'Line continuation .* expected level should be 4'
    // violation 3 lines above 'Line continuation .* expected level should be 4'
    static class Test2 {}

    /**
     *
     * <a href="https://checkstyle.org/checks/javadoc/javadoctagcontinuationindentation.html">
     * JavadocTagContinuationIndentation: Checks the indentation of the continuation lines
     * </a>
     */
    static class Test3 {}

}
