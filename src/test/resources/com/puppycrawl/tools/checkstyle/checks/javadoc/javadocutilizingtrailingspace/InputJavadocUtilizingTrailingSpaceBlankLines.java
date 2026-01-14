/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for blank/empty lines handling.
 */
public class InputJavadocUtilizingTrailingSpaceBlankLines {

    /**
     * First paragraph of text.
     *
     * Second paragraph after blank line.
     */
    public void paragraphsWithBlank() { }

    /**
     *
     * Content after initial blank line.
     */
    public void blankAtStart() { }

    /**
     * Content before final blank line.
     *
     */
    public void blankAtEnd() { }

    /**
     * Line one.
     *
     *
     * Line after two blank lines.
     */
    public void multipleBlankLines() { }

    /**
     *
     *
     *
     * Content after many blank lines.
     */
    public void manyBlanksAtStart() { }

    /**
     * Description.
     *
     * @param value the input value
     */
    public void blankBeforeTag(int value) { }

    /**
     * @param a first
     *
     * @param b second
     */
    public void blankBetweenTags(int a, int b) { }

    /**
     *     Blank lines with only whitespace.
     *
     *     Another line after blank.
     */
    public void blankLinesWithWhitespace() { }
}
