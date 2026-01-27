/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for HTML structural tags at line start (should be ignored).
 */
public class InputJavadocUtilizingTrailingSpaceHtmlTags {

    /**
     * <p>
     * Paragraph content that follows an HTML paragraph tag on its own line.
     * </p>
     */
    public void paragraphTag() { }

    /**
     * <div>
     * Content inside a div element that starts with the structural tag.
     * </div>
     */
    public void divTag() { }

    /**
     * List of items:
     * <ul>
     * <li>First item in the unordered list.</li>
     * <li>Second item in the unordered list.</li>
     * </ul>
     */
    public void unorderedList() { }

    /**
     * Ordered steps:
     * <ol>
     * <li>Step one of the process.</li>
     * <li>Step two of the process.</li>
     * </ol>
     */
    public void orderedList() { }

    /**
     * <table>
     * <tr>
     * <th>Header One</th>
     * <th>Header Two</th>
     * </tr>
     * <tr>
     * <td>Data One</td>
     * <td>Data Two</td>
     * </tr>
     * </table>
     */
    public void tableStructure() { }

    /**
     * <blockquote>
     * A quoted passage that starts with a blockquote tag.
     * </blockquote>
     */
    public void blockquoteTag() { }

    /**
     * <h3>Section Heading</h3>
     * Content after an h3 heading tag.
     */
    public void headingTag() { }

    /**
     * <p>First paragraph.</p>
     * <p>Second paragraph with more content.</p>
     */
    public void multipleParagraphs() { }
}
