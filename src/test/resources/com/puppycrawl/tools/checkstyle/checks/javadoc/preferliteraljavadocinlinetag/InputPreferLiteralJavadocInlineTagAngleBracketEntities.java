/*
PreferLiteralJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferliteraljavadocinlinetag;

// 5 violations 7 lines below:
//  'Prefer literal or code javadoc inline tag over '&amp;'.'
//  'Prefer literal or code javadoc inline tag over '&quot;'.'
//  'Prefer literal or code javadoc inline tag over '&apos;'.'
//  'Prefer literal or code javadoc inline tag over '&lt;'.'
//  'Prefer literal or code javadoc inline tag over '&gt;'.'
/**
 * Prefer &amp; , &quot; , &apos; &lt; &gt; for special characters.
 */
public class InputPreferLiteralJavadocInlineTagAngleBracketEntities {

    // 2 violations 4 lines below:
    //  'Prefer literal or code javadoc inline tag over '&lt;'.'
    //  'Prefer literal or code javadoc inline tag over '&gt;'.'
    /**
     * Type parameter is &lt;E&gt; here.
     */
    public void method() {
    }

    // 2 violations 4 lines below:
    //  'Prefer literal or code javadoc inline tag over '&lt;'.'
    //  'Prefer literal or code javadoc inline tag over '&gt;'.'
    /**
     * &lt;T&gt; generic type.
     */
    public void entityAtStart() {
    }

    // violation 2 lines below 'Prefer literal or code javadoc inline tag over '&gt;'.'
    /**
     * &gt; is the greater than sign.
     * &apos; and &quot; is used for quotes.
     */
    public void gtEntityAtStart() {
        // 2 violations 3 lines above:
        //  'Prefer literal or code javadoc inline tag over '&apos;'.'
        //  'Prefer literal or code javadoc inline tag over '&quot;'.'
    }

    // 2 violations 4 lines below:
    //  'Prefer literal or code javadoc inline tag over '&lt;'.'
    //  'Prefer literal or code javadoc inline tag over '&gt;'.'
    /**
     * <b>&lt;T&gt;</b> bold generic.
     * <p> if (a &amp; b &gt; c) then do something. </p>
     */
    public void entityDirectlyAfterHtmlTag() {
        // 2 violations 3 lines above:
        //  'Prefer literal or code javadoc inline tag over '&amp;'.'
        //  'Prefer literal or code javadoc inline tag over '&gt;'.'
    }

    // violation 2 lines below 'Prefer literal or code javadoc inline tag over '&gt;'.'
    /**
     * <i>&gt;</i> italic greater than.
     */
    public void gtEntityDirectlyAfterHtmlTag() {
    }

}
