/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;
// violation 5 lines below 'Line continuation .* expected level should be 4'
// violation 7 lines below 'Line continuation .* expected level should be 4'
/**
 *
 * @see reference <a href="https://checkstyle.org/checks/javadoc">
 *   JavadocTagContinuationIndentation: Checks the indentation of the continuation</a>
 *
 * @see <a href="https://checkstyle.org/checks/javadoc/javadoctagcontinuationindentation.html">
 *   JavadocTagContinuationIndentation: Checks the indentation of the continuation lines.</a>
 */
public class InputJavadocTagContinuationIndentationCheckHtml {

    // violation 6 lines below 'Line continuation .* expected level should be 4'
    // violation 6 lines below 'Line continuation .* expected level should be 4'
    // violation 6 lines below 'Line continuation .* expected level should be 4'
    /**
     *
     * @see <a
     *   href="https://checkstyle.org/checks/javadoc/javadoctagcontinuationindentation.html">
     *   JavadocTagContinuationIndentation: Checks the indentation of the continuation lines
     *   </a>
     */
    static class Test {}

    /**
     * This method demonstrates inline tags, which should be skipped.
     * {@link java.util.List} {@code String} {@literal example}
     *
     * @see <a href="https://example.com">
     *     This should follow correct indentation.</a>
     */
    static class InlineTagTest {}

    // violation 7 lines below 'Line continuation .* expected level should be 4'
    // violation 7 lines below 'Line continuation .* expected level should be 4'
    // violation 7 lines below 'Line continuation .* expected level should be 4'
    /**
     * Something.
     *
     * @param source <a href=
     * "https://example.com/data-source">
     * Data Source Documentation
     * </a>
     */
    static void readData(String source) {}

    // violation 7 lines below 'Line continuation .* expected level should be 4'
    // violation 7 lines below 'Line continuation .* expected level should be 4'
    // violation 7 lines below 'Line continuation .* expected level should be 4'
    /**
     * Something.
     *
     * @deprecated Use {@link #getUserInfo()} instead. More details
     *   <a href="https://example.com/api-changes">
     *   here
     *   </a>
     */
    @Deprecated
    static void getUserData() {}

    // violation 5 lines below 'Line continuation .* expected level should be 4'
    // violation 5 lines below 'Line continuation .* expected level should be 4'
    /**
     *
     * @see <a href="https://checkstyle.org/checks/javadoc/javadoctagcontinuationindentation.html">
     * JavadocTagContinuationIndentation: Checks the indentation of the continuation lines
     * </a>
     */
    static class Test2 {}

    /**
     * @see
     * <a href="https://checkstyle.org"> Javadoc </a> // ok until #16656
     */
    static class Test3 {}

    /**
     * @see
     *   <a href="https://checkstyle.org"> Javadoc </a> // ok until #16656
     */
    static class Test4 {}

    /**
     * @see
     *     <a href="https://checkstyle.org"> Javadoc </a>
     */
    static class Test5 {}

    /**
     * @see
     *       <a href="https://check.org"> Java </a> // ok, Indentation exceeding offset is allowed.
     */
    static class Test6 {}

    /**
     * <a href="https://checkstyle.org/checks/javadoc/javadoctagcontinuationindentation.html">
     * JavadocTagContinuationIndentation: Checks the indentation of the continuation lines </a>
     */
    static class Test7 {}

}
