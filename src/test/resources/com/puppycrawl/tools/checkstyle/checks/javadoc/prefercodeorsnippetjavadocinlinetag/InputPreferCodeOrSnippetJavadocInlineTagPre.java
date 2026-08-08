/*
PreferCodeOrSnippetJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.prefercodeorsnippetjavadocinlinetag;

public class InputPreferCodeOrSnippetJavadocInlineTagPre {

    // violation 2 lines below 'Use code or snippet inline tags instead of 'pre' tag.'
    /**
     * <pre> This is a single line pre.</pre>
     */
    public void badMethod() {
    }

    /**
     * {@code This is a single line pre.}
     * {@snippet :
     *      This is a single line pre.
     * }
     */
    public void goodMethod() {
    }

    // violation 2 lines below "Use snippet inline tag instead of 'pre' tag."
    /**
     * <pre>
     *      This is a multi-line pre.
     * </pre>
     */
    public void badMethod1() {
    }

    /**
     * {@snippet :
     *      This is a multi-line pre.
     * }
     */
    public void goodMethod1() {
    }

    /**
     * This is not flagged due to void element.
     * <pre/>
     */
    public void method2() {
    }

    // violation 2 lines below "Use snippet inline tag instead of 'pre' tag."
    /**
     * <pre>
     *  There is some text.</pre>
     *
     * <pre> There is also text.
     * </pre>
     */
    public void method3() {
    // violation 4 lines above "Use snippet inline tag instead of 'pre' tag."
    }

}
