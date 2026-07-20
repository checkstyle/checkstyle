/*
PreferJavadocInlineTags
jdkVersion = (default)25
violateExecutionOnNonTightHtml = (default)false


*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.preferjavadocinlinetags;

/**
 * Test that HTML patterns inside inline tags are not flagged.
 */
public class InputPreferJavadocInlineTagsSnippetTag {

    /**
     * Nested HTML inside snippet:
     * {@snippet :
     * <a href="#method">link</a>
     * <pre>Hello</pre>
     * &lt;
     * }
     */
    public void nestedInsideSnippet() {
    }

}
