/*
PreferJavadocInlineTags
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferjavadocinlinetags;

/**
 * Edge cases to cover null paths.
 * <!-- HTML comment -->
 * <p>Some paragraph</p>
 *
 * Test with text only, no HTML tags.
 */
public class InputPreferJavadocInlineTagsEdgeCases {

    /**
     * Just plain text without any tags.
     * Multiple lines of text.
     */
    public void plainText() {
    }

    /**
     * Block-level HTML tags with no Javadoc equivalent:
     * <p>Paragraph</p>
     * <div>Division</div>
     * <blockquote>Quote</blockquote>
     */
    public void blockLevelTags() {
    }

    /**
     * List tags with no Javadoc equivalent:
     * <ul>
     *   <li>Item one</li>
     * </ul>
     * <ol>
     *   <li>First</li>
     * </ol>
     */
    public void listTags() {
    }

    /**
     * Table tags with no Javadoc equivalent:
     * <table>
     *   <tr><th>Header</th></tr>
     *   <tr><td>Data</td></tr>
     * </table>
     */
    public void tableTags() {
    }

    /**
     * External links are allowed (no Javadoc equivalent):
     * <a href="http://docs.oracle.com">Oracle Docs</a>
     * <a href="https://github.com/checkstyle">GitHub</a>
     */
    public void externalLinks() {
    }

    /**
     * Correct usage, already using Javadoc inline tags:
     * Returns {@code true} if valid.
     * Use {@literal <} and {@literal >} for brackets.
     */
    public void correctUsage() {
    }

    /**
     * Content inside pre blocks should be ignored:
     * <pre>
     * &lt;code&gt;sample&lt;/code&gt;
     * &lt;a href="#method"&gt;link&lt;/a&gt;
     * </pre>
     */
    public void insidePreBlock() {
    }

    /**
     * Content inside {@code} inline tag should be ignored:
     * Example: {@code <code>text</code>}
     * Link syntax: {@code <a href="#">link</a>}
     * Entities: {@code &lt;T&gt;}
     */
    public void insideCodeTag() {
    }

    /**
     * Content inside {@literal } inline tag should be ignored:
     * Show: {@literal <code>text</code>}
     * Link: {@literal <a href="#">link</a>}
     */
    public void insideLiteralTag() {
    }

    /**
     * Self-closing/void elements:
     * Line break<br/>
     * Horizontal rule<hr/>
     */
    public void voidElements() {
    }

    /**
     * HTML entities that are NOT {@code &lt; or &gt; }
     * Non-breaking space: &nbsp;
     * Ampersand: &amp;
     * Quote: &quot;
     * Copyright: &copy;
     */
    public void otherEntities() {
    }
}
