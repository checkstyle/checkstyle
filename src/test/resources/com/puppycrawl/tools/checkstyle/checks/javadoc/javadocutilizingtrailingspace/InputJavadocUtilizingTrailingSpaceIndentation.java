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

    // violation 2 lines below 'Line is smaller than 80 characters (found 19).'
    /**
     * Normal text.
     *     Indented continuation line (leading whitespace ignored for checking).
     */
    public void indentedContinuation() { }

    // violation 3 lines below 'Line is smaller than 80 characters (found 35).'
    // violation 3 lines below 'Line is smaller than 80 characters (found 55).'
    /**
     * List items with indentation:
     *     - First indented item with description text.
     *     - Second indented item with more text here.
     */
    public void indentedListItems() { }

    // violation 2 lines below 'Line is smaller than 80 characters (found 19).'
    /**
     * @param value
     *     The parameter description is indented on its own line.
     */
    public void indentedParamValue(int value) { }

    // violation 2 lines below 'Line is smaller than 80 characters (found 14).'
    /**
     * @return
     *     An indented return description that spans appropriately.
     */
    public int indentedReturn() {
        return 0;
    }

    // violation 4 lines below 'Line is smaller than 80 characters (found 33).'
    // violation 4 lines below 'Line is smaller than 80 characters (found 19).'
    // violation 4 lines below 'Line is smaller than 80 characters (found 21).'
    /**
     * Nested indentation levels:
     *   Level one.
     *     Level two.
     *       Level three.
     */
    public void nestedIndentation() { }

    // violation 2 lines below 'Line is smaller than 80 characters (found 48).'
    /**
     *   Entire javadoc starts with indentation.
     *   And continues with consistent indentation.
     */
    public void consistentIndentation() { }

    // violation 4 lines below 'Line is smaller than 80 characters (found 13).'
    // violation 4 lines below 'Line is smaller than 80 characters (found 31).'
    // violation 4 lines below 'Line is smaller than 80 characters (found 34).'
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
