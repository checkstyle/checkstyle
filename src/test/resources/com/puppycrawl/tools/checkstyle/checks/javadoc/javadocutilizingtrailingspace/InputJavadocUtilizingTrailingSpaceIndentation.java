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

    // violation 2 below 'Line is smaller than'
    /**
     * Normal text.
     *     Indented continuation line (leading whitespace ignored for checking).
     */
    public void indentedContinuation() { }

    // violation below 'Line is smaller than'
    // violation 2 below 'Line is smaller than'
    /**
     * List items with indentation:
     *     - First indented item with description text.
     *     - Second indented item with more text here.
     */
    public void indentedListItems() { }

    // violation 2 below 'Line is smaller than'
    /**
     * @param value
     *     The parameter description is indented on its own line.
     */
    public void indentedParamValue(int value) { }

     // violation 2 below 'Line is smaller than'
    /**
     * @return
     *     An indented return description that spans appropriately.
     */
    public int indentedReturn() {
        return 0;
    }

    // violation 2 below 'Line is smaller than'
    // violation 3 below 'Line is smaller than'
    // violation 4 below 'Line is smaller than'
    /**
     * Nested indentation levels:
     *   Level one.
     *     Level two.
     *       Level three.
     */
    public void nestedIndentation() { }

    // violation 2 below 'Line is smaller than'
    /**
     *   Entire javadoc starts with indentation.
     *   And continues with consistent indentation.
     */
    public void consistentIndentation() { }

    // violation 2 below 'Line is smaller than'
    // violation 3 below 'Line is smaller than'
    // violation 5 below 'Line is smaller than'
    /**
     * Mixed:
     *     Some lines indented.
     * Others not indented at all.
     *     And back to indented again.
     */
    public void mixedIndentation() { }

    /**
     * HTML with indentation:
     *     <ul>
     *         <li>Indented list item one.</li>
     *         <li>Indented list item two.</li>
     *     </ul>
     */
    public void indentedHtml() { }
}
