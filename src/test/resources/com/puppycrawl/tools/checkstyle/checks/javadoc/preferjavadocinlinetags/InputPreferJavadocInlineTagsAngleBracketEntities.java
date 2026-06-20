/*
PreferJavadocInlineTags
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferjavadocinlinetags;

// 2 violations 4 lines below:
//  'Prefer Javadoc inline tag '{@literal <}' over '&lt;'.'
//  'Prefer Javadoc inline tag '{@literal >}' over '&gt;'.'
/**
 * Use &lt;T&gt; for generics.
 */
public class InputPreferJavadocInlineTagsAngleBracketEntities {

    // 2 violations 4 lines below:
    //  'Prefer Javadoc inline tag '{@literal <}' over '&lt;'.'
    //  'Prefer Javadoc inline tag '{@literal >}' over '&gt;'.'
    /**
     * Type parameter is &lt;E&gt; here.
     */
    public void method() {
    }

    // 2 violations 4 lines below:
    //  'Prefer Javadoc inline tag '{@literal <}' over '&lt;'.'
    //  'Prefer Javadoc inline tag '{@literal >}' over '&gt;'.'
    /**
     * &lt;T&gt; generic type.
     */
    public void entityAtStart() {
    }

    // violation 2 lines below 'Prefer Javadoc inline tag '{@literal >}' over '&gt;'.'
    /**
     * &gt; is the greater than sign.
     */
    public void gtEntityAtStart() {
    }

    // 2 violations 4 lines below:
    //  'Prefer Javadoc inline tag '{@literal <}' over '&lt;'.'
    //  'Prefer Javadoc inline tag '{@literal >}' over '&gt;'.'
    /**
     * <b>&lt;T&gt;</b> bold generic.
     */
    public void entityDirectlyAfterHtmlTag() {
    }

    // violation 2 lines below 'Prefer Javadoc inline tag '{@literal >}' over '&gt;'.'
    /**
     * <i>&gt;</i> italic greater than.
     */
    public void gtEntityDirectlyAfterHtmlTag() {
    }

}
