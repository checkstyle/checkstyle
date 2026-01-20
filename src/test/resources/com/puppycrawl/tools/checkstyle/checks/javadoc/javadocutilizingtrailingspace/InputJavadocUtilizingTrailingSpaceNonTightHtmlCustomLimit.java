/*
JavadocUtilizingTrailingSpace
lineLimit = 50
violateExecutionOnNonTightHtml = true

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for non-tight HTML with custom limit.
 */
public class InputJavadocUtilizingTrailingSpaceNonTightHtmlCustomLimit {

    /**
     * Short line.
     */
    public void shortLine() { }

    /**
     * This line exceeds the fifty char limit set.
     */
    public void longLine() { }

    // violation 2 below 'Line is smaller than'
    /**
     * Non-tight list:
     * <ul>
     * <li>First
     * <li>Second
     * </ul>
     */
    public void nonTightList() { }

    // violation 2 below 'Line is smaller than'
    /**
     * A
     * short.
     */
    public void tooShort() { }

    // violation 2 below 'Line is smaller than'
    /**
     * Table:
     * <table>
     * <tr><td>Cell
     * </table>
     */
    public void nonTightTable() { }

    // violation 3 below 'Line is smaller than'
    // violation 4 below 'Line is smaller than'
    /**
     * Short
     * with <p> non-tight paragraph.
     */
    public void shortWithParagraph() { }
}
