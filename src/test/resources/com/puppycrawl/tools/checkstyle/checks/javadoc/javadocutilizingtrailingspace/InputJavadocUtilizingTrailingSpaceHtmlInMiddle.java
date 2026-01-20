/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for HTML tags in the middle of text lines (not ignored).
 */
public class InputJavadocUtilizingTrailingSpaceHtmlInMiddle {

    /**
     * This has a <b>bold</b> word in the middle of the line.
     */
    public void boldInMiddle() { }

    /**
     * Use the <code>getValue()</code> method to retrieve it.
     */
    public void codeInMiddle() { }

    /**
     * The text has <em>emphasis</em> here and continues normally.
     */
    public void emphasisInMiddle() { }

    /**
     * Click <a href="http://example.com">here</a> for more information.
     */
    public void anchorInMiddle() { }

     // violation 2 below 'Line is longer than'
    /**
     * Very long line with <b>bold text</b> that makes it exceed the eighty character line limit here.
     */
    public void longLineWithHtml() { }

    /**
     * Short with <i>italic</i> and <b>bold</b> mixed together.
     */
    public void multipleInlineTags() { }

    /**
     * The <tt>monospace</tt> text is used for technical terms.
     */
    public void monospaceInMiddle() { }

    /**
     * Use <strong>strong emphasis</strong> for important information.
     */
    public void strongInMiddle() { }

    /**
     * Small example: x<sup>2</sup> + y<sup>2</sup> = z<sup>2</sup>
     */
    public void superscript() { }

    /**
     * Water is H<sub>2</sub>O.
     */
    public void subscript() { }
}
