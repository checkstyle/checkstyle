/*
PreferLiteralJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferliteraljavadocinlinetag;

/**
 * Test that HTML patterns inside inline tags are not flagged.
 */
public class InputPreferLiteralJavadocInlineTagSkipInsideInlineTags {
    /**
     * Entities: {@code &lt; , &gt;, &amp;, &quot;, &apos;}.
     */
    public void entitiesInsideCodeTag() {}

    /**
     * Use {@literal <code>text</code>} syntax.
     */
    public void htmlInsideLiteralTag() {}

    /**
     * Display: {@literal &lt;}, {@literal &gt;},
     * {@literal &amp;}, {@literal &quot;}, {@literal &apos;}
     */
    public void entitiesInsideLiteralTag() {}

    /**
     * Mixed: {@code <code>foo&lt;</code>} and {@literal &lt;bar&gt;}.
     */
    public void mixedInlineTags() {}
}
