/*
PreferCodeOrSnippetJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.prefercodeorsnippetjavadocinlinetag;

public class InputPreferCodeOrSnippetJavadocInlineTagOther {

    /**
     * <div>
     *     This tag is not flagged.
     * </div>
     *
     * <ul>
     *     <li>No error</li>
     * </ul>
     *
     * <p>
     *     Paragraph is not flagged.
     * </p>
     */
    public void method() {
    }

    /**
     * <pre>
     *      <ul>
     *          <li>* Start with star is not flagged.</li>
     *          <li></li>
     *      </ul>
     * </pre>
     * <pre>
     *      <ul>
     *          <li>{@code * Start with star is not flagged.}</li>
     *      </ul>
     * </pre>
     */
    public void method1() {
    }

    /**
     * <code>This is a left curly {.</code>
     * <code>This is a right curly }.</code>
     */
    public void method2() {
    }

    /**
     * <pre>{@code
     * /**
     *   * This is a javadoc inside javadoc start with star.
     *   * /
     * }</pre>
     */
    public void method3() {
    }

    /**
     * <code>This is a right curly } and this is left { </code>
     * <code> This is some text with unclosed code tag.
     */
    public void method4() {
    }

}
