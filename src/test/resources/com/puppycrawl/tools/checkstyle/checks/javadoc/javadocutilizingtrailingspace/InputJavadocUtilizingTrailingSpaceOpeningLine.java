/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for Javadoc content that starts on the opening line or spans lines.
 */
public class InputJavadocUtilizingTrailingSpaceOpeningLine {

    // violation below 'Line under-utilized (33/80). Words from below could be moved up'
    /** Text on the opening line.
     *  More text that could be moved up to the opening line.
     */
    public void textOnOpeningLine() { }

    /** Text on the opening line that fills most of the available space before
     *  the line break.
     */
    public void correctedTextOnOpeningLine() { }

    /**{@inheritDoc}*/
    @Override
    public String toString() {
        return "";
    }

    /** @return value */
    public int blockTagOnOpeningLine() {
        return 0;
    }

    /**@return value */
    public int blockTagRightAfterOpening() {
        return 0;
    }

    /**
     * Inline tag that spans more than one line {@link
     * String}
     */
    public void multiLineInlineTag() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 85).'
    /**
     * The text before this inline tag is long enough to go over the limit by itself {@link
     * String}
     */
    public void multiLineInlineTagTooLong() { }
}
