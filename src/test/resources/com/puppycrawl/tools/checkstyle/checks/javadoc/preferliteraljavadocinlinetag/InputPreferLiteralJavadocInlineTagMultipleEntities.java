/*
PreferLiteralJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferliteraljavadocinlinetag;

// 4 violations 6 lines below:
//  'Prefer literal or code javadoc inline tag over '&lt;'.'
//  'Prefer literal or code javadoc inline tag over '&gt;'.'
//  'Prefer literal or code javadoc inline tag over '&lt;'.'
//  'Prefer literal or code javadoc inline tag over '&gt;'.'
/**
 * Multiple: &lt;T&gt; and &lt;E&gt; generics.
 */
public class InputPreferLiteralJavadocInlineTagMultipleEntities {

    // 4 violations 6 lines below:
    //  'Prefer literal or code javadoc inline tag over '&amp;'.'
    //  'Prefer literal or code javadoc inline tag over '&amp;'.'
    //  'Prefer literal or code javadoc inline tag over '&amp;'.'
    //  'Prefer literal or code javadoc inline tag over '&amp;'.'
    /**
     * if (a &amp;&amp; b || c &amp;&amp; d)
     */
    public void method() { }
}
