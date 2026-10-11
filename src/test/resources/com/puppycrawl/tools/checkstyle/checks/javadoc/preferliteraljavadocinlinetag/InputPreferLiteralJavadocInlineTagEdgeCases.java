/*
PreferLiteralJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferliteraljavadocinlinetag;

/**
 * Edge cases to cover null paths.
 * <!-- HTML comment -->
 * <p>Some paragraph</p>
 *
 * Test with text only, no HTML tags.
 */
public class InputPreferLiteralJavadocInlineTagEdgeCases {

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
     * Use {@literal ""} and {@literal ''} for quotes.
     * Use {@literal &}.
     */
    public void correctUsage() {
    }

    /**
     * Content inside pre and code blocks should be ignored:
     * <pre>
     * &lt;code&gt;sample&lt;/code&gt;
     * &lt;a href="#method"&gt;link&lt;/a&gt;
     * if(a &amp;&amp; b) {c = &apos;d&apos;}
     * </pre>
     * <code>
     * &lt;code&gt;sample&lt;/code&gt;
     * &lt;a href="#method"&gt;link&lt;/a&gt;
     * if(a &amp;&amp; b) {c = &apos;d&apos;}
     * </code>
     */
    public void insidePreOrCodeBlock() {
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
     * Content inside inline tags is allowed:
     * Literal: {@literal <code>&lt;T&gt;</code>}
     * {@snippet :
     *      &lt;T&gt;
     *      String str = &quot;test&quot;
     *      &apos;a&apos;
     *
     * }
     */
    public void insideInlineTags() { }
}
