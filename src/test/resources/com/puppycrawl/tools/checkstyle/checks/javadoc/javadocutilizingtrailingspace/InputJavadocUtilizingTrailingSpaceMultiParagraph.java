/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for multi-paragraph Javadoc comments.
 */
public class InputJavadocUtilizingTrailingSpaceMultiParagraph {

    /**
     * First paragraph describes the basic functionality of this method.
     *
     * <p>Second paragraph provides additional details about the implementation
     * and any special considerations that users should be aware of.
     *
     * <p>Third paragraph discusses edge cases and exceptional situations.
     */
    public void threeParagraphs() { }

    /**
     * Overview paragraph.
     *
     * <p>Details paragraph.
     *
     * <p>Usage paragraph.
     *
     * <p>Examples paragraph.
     */
    public void fourParagraphs() { }

    /**
     * <p>Paragraph starting with HTML tag.
     *
     * <p>Another paragraph with HTML tag at start.
     */
    public void htmlParagraphStarts() { }

    /**
     * Introduction.
     *
     * <p>
     * Paragraph with the p tag on its own line, content follows. This allows
     * for cleaner formatting in some coding styles used.
     * </p>
     */
    public void separatePTagLine() { }

    /**
     * Short intro.
     *
     * <p>A medium length second paragraph that fills the line appropriately.
     *
     * @param value the input value to process with this method
     * @return the result of processing the input value provided
     */
    public int paragraphsWithTags(int value) {
        return value;
    }

    // violation 2 below 'Line is smaller than'
    /**
     * A short
     * first paragraph that violates.
     *
     * <p>A proper second paragraph that uses space efficiently.
     */
    public void mixedViolations() { }
}
