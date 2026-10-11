/*
PreferCodeOrSnippetJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.prefercodeorsnippetjavadocinlinetag;

public class InputPreferCodeOrSnippetJavadocInlineTagMix2 {

    // violation 2 lines below "Use code or snippet inline tags instead of 'pre' tag."
    /**
     * <pre>{@code int y = 10;}</pre>
     */
    public void badMethod() {
    }

    /**
     * {@code int y = 10;}
     * {@snippet :
     *      int y = 10;
     * }
     */
    public void goodMethod() {
    }

    // violation 2 lines below "Use snippet inline tag instead of 'pre' tag."
    /**
     * <pre> {@code
     *      int y = 10;}
     * </pre>
     */
    public void badMethod1() {
    }

    /**
     * {@snippet :
     *      int y = 10;
     * }
     */
    public void goodMethod1() {
    }

}
