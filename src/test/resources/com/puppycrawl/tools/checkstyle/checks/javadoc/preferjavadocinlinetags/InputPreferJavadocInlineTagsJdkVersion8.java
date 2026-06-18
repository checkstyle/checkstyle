/*
PreferJavadocInlineTags
violateExecutionOnNonTightHtml = (default)false
jdkVersion = 1.8


*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.preferjavadocinlinetags;

public class InputPreferJavadocInlineTagsJdkVersion8 {

    /**
     * <pre>
     *     Some random text.
     * </pre>
     */
    public void method() {}

    // violation 2 lines below 'Prefer Javadoc inline tag '{@code ...}' over '<code>'.'
    /**
     * <code>Some text</code>
     */
    public void method1() {}

}
