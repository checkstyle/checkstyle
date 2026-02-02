/*
PreferJavadocInlineTags
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferjavadocinlinetags;

public class InputPreferJavadocInlineTagsMixed {

    // violation 2 lines below 'Prefer Javadoc inline tag '{@code ...}' over '<code>'.'
    /**
     * Returns <code>true</code> if valid.
     */
    public void method1() {
    }

    // violation 2 lines below 'Prefer Javadoc inline tag '{@link ...}' over '<a href="#...">'.'
    /**
     * See <a href="#validate">validate</a>.
     */
    public void method2() {
    }

    // 2 violations 4 lines below:
    //  'Prefer Javadoc inline tag '{@literal <}' over '&lt;'.'
    //  'Prefer Javadoc inline tag '{@literal >}' over '&gt;'.'
    /**
     * Use &lt;T&gt; for generics.
     */
    public void method3() {
    }

    /** Target method. */
    public void validate() {
    }
}
