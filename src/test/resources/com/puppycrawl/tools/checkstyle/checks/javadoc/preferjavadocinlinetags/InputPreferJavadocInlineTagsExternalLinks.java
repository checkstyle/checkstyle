/*
PreferJavadocInlineTags
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferjavadocinlinetags;

/**
 * External links should not be flagged.
 * See <a href="https://checkstyle.org">Checkstyle</a> for more info.
 * Also see <a href="http://example.com">Example</a>.
 */
public class InputPreferJavadocInlineTagsExternalLinks {

    /**
     * Link to <a href="https://docs.oracle.com">Oracle Docs</a>.
     */
    public void externalLink() {
    }
}
