/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = true

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for non-tight HTML with violation enabled.
 */
public class InputJavadocUtilizingTrailingSpaceNonTightHtml {

    // violation 2 below 'Line is smaller than'
    /**
     * A short
     * line here.
     */
    public void shortLine() { }

    // violation 2 below 'Line is longer than'
    /**
     * This line is way too long and should trigger a violation because it exceeds the limit here.
     */
    public void longLine() { }

    // violation 2 below 'Line is smaller than'
    /**
     * List with non-tight HTML:
     * <ul>
     * <li>First item
     * <li>Second item
     * </ul>
     */
    public void nonTightList() { }

    // violation 2 below 'Line is smaller than'
    /**
     * Another list example:
     * <ol>
     * <li>Item one
     * <li>Item two
     * </ol>
     */
    public void nonTightOrderedList() { }

    // violation 2 below 'Line is smaller than'
    /**
     * Paragraph with non-tight HTML:
     * <p>
     * Some content here.
     */
    public void nonTightParagraph() { }

    // violation 3 below 'Line is smaller than'
    // violation 4 below 'Line is smaller than'
    /**
     * Short
     * text with non-tight <p> tag.
     */
    public void shortWithNonTight() { }
}
