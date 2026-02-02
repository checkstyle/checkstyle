/*
PreferJavadocInlineTags
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferjavadocinlinetags;

/**
 * Content inside pre tags should not be flagged.
 *
 * <pre>
 * &lt;code&gt;example&lt;/code&gt;
 * &lt;T&gt; generic type
 * <a href="#method">link</a>
 * </pre>
 */
public class InputPreferJavadocInlineTagsInsidePreSkipped {

    /**
     * Example:
     *
     * <pre>
     * List&lt;String&gt; list = new ArrayList&lt;&gt;();
     * </pre>
     */
    public void method() {
    }
}
