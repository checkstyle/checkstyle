/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for leading whitespace/indentation handling.
 */
public class InputJavadocUtilizingTrailingSpaceIndentation {

    // violation 2 lines below 'Line under-utilized (19/80). Words from below could be moved up'
    /**
     * Normal text.
     *     Indented continuation line (leading whitespace ignored for checking).
     */
    public void indentedContinuation() { }

    /**
     * Normal text. Indented continuation line (leading whitespace ignored for
     *      checking).
     */
    public void correctedIndentedContinuation() { }

    /**
     * @param value
     *     The parameter description is indented on its own line.
     */
    public void indentedParamValue(int value) { }

    /**
     * @return
     *     An indented return description that spans appropriately.
     */
    public int indentedReturn() {
        return 0;
    }

    /**
     * HTML with indentation:
     *     <ul>
     *         <li>Indented list item one.</li>
     *         <li>Indented list item two.</li>
     *     </ul>
     */
    public void indentedHtml() { }
}
