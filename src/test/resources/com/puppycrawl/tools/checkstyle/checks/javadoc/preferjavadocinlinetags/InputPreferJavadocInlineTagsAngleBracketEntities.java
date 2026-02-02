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
}
