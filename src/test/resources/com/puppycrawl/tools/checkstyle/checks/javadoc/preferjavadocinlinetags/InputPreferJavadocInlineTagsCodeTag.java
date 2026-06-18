/*
PreferJavadocInlineTags
violateExecutionOnNonTightHtml = (default)false
jdkVersion = (default)25


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferjavadocinlinetags;

// violation 2 lines below 'Prefer Javadoc inline tag '{@code ...}' over '<code>'.'
/**
 * Returns <code>true</code> if valid.
 */
public class InputPreferJavadocInlineTagsCodeTag {

    // violation 2 lines below 'Prefer Javadoc inline tag '{@code ...}' over '<code>'.'
    /**
     * Returns <code>false</code> when empty.
     */
    public boolean isEmpty() {
        return false;
    }

    // violation 2 lines below 'Prefer Javadoc inline tag '{@code ...}' over '<code>'.'
    /**
     * The value is <code>null</code> by default.
     */
    public String value;
}
