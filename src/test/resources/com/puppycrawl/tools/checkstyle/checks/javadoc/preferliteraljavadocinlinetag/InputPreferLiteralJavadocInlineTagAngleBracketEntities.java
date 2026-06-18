/*
PreferLiteralJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferliteraljavadocinlinetag;

// 2 violations 4 lines below:
//  'Prefer literal or code javadoc inline tag over '&lt;'.'
//  'Prefer literal or code javadoc inline tag over '&gt;'.'
/**
 * Use &lt;T&gt; for generics.
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
     */
    public void gtEntityAtStart() {
    }

    // 2 violations 4 lines below:
    //  'Prefer literal or code javadoc inline tag over '&lt;'.'
    //  'Prefer literal or code javadoc inline tag over '&gt;'.'
    /**
     * <b>&lt;T&gt;</b> bold generic.
     */
    public void entityDirectlyAfterHtmlTag() {
    }

    // violation 2 lines below 'Prefer literal or code javadoc inline tag over '&gt;'.'
    /**
     * <i>&gt;</i> italic greater than.
     */
    public void gtEntityDirectlyAfterHtmlTag() {
    }

}
