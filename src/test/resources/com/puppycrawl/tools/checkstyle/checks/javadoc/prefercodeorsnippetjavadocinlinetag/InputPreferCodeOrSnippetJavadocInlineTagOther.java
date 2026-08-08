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
}
