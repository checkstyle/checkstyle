/*
PreferLiteralJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferliteraljavadocinlinetag;

public class InputPreferLiteralJavadocInlineTagSupplementaryCharacters {

    // violation 2 lines below 'Prefer literal or code javadoc inline tag over '&lt;''
    /**
     * Ascii text &lt; here.
     */
    void ascii() {
    }

    // violation 2 lines below 'Prefer literal or code javadoc inline tag over '&lt;''
    /**
     * Emoji 😀 text &lt; here.
     */
    void emoji() {
    }

    // 2 violations 4 lines below:
    //    'Prefer literal or code javadoc inline tag over '&lt;''
    //    'Prefer literal or code javadoc inline tag over '&gt;''
    /**
     * Letters 𝒜𝒜 &lt;T&gt; here.
     */
    void letters() {
    }
}
