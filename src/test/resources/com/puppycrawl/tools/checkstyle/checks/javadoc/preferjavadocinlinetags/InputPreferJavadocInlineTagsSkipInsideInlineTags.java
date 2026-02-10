/*
PreferJavadocInlineTags
violateExecutionOnNonTightHtml = (default)false


*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.preferjavadocinlinetags;

/**
 * Test that HTML patterns inside inline tags are not flagged.
 */
public class InputPreferJavadocInlineTagsSkipInsideInlineTags {

    /**
     * Use {@code <code>text</code>} instead of HTML.
     */
    public void htmlInsideCodeTag() {}

    /**
     * Example: {@code <a href="#method">link</a>}.
     */
    public void linkInsideCodeTag() {}

    /**
     * Entities: {@code &lt;T&gt;} for generics.
     */
    public void entitiesInsideCodeTag() {}

    /**
     * Use {@literal <code>text</code>} syntax.
     */
    public void htmlInsideLiteralTag() {}

    /**
     * Show: {@literal <a href="#">link</a>}.
     */
    public void linkInsideLiteralTag() {}

    /**
     * Display: {@literal &lt;} and {@literal &gt;}.
     */
    public void entitiesInsideLiteralTag() {}

    /**
     * Mixed: {@code <code>foo</code>} and {@literal &lt;bar&gt;}.
     */
    public void mixedInlineTags() {}
}
