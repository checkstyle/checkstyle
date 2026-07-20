/*
PreferJavadocInlineTags
jdkVersion = (default)25
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferjavadocinlinetags;

// violation 4 lines below 'Prefer Javadoc inline tag '{@snippet ...}' over '<pre>'.'
/**
 * Content inside pre tags should not be flagged.
 *
 * <pre>
 * &lt;code&gt;example&lt;/code&gt;
 * &lt;T&gt; generic type
 * <a href="#method">link</a>
 * </pre>
 */
public class InputPreferJavadocInlineTagsForPre {

    // violation 4 lines below 'Prefer Javadoc inline tag '{@snippet ...}' over '<pre>'.'
    /**
     * Example:
     *
     * <pre>
     * List&lt;String&gt; list = new ArrayList&lt;&gt;();
     * </pre>
     */
    public void method() {
    }

    // violation 3 lines below 'Prefer Javadoc inline tag '{@snippet ...}' over '<pre>'.'
    /**
     * Nested HTML inside pre:
     * <pre>
     * <code><a href="#method">link</a></code>
     * <pre>Hello</pre>
     * </pre>
     */
    public void nestedInsidePre() {
    }

    // violation 2 lines below 'Prefer Javadoc inline tag '{@snippet ...}' over '<pre>'.'
    /**
     * <pre>
     * <ul>
     *   <li>&lt;T&gt; type param</li>
     * </ul>
     * </pre>
     */
    public void deeplyNestedInsidePre() {
    }

}
