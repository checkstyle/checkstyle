/*
PreferCodeOrSnippetJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.prefercodeorsnippetjavadocinlinetag;

public class InputPreferCodeOrSnippetJavadocInlineTagCode {

    // violation 2 lines below 'Use code or snippet inline tags instead of 'code' tag.'
    /**
     * <code> int x = 10; </code>
     */
    public void badMethod() {
    }

    /**
     * {@code int x = 10;}
     * {@snippet :
     *      int x = 10;
     * }
     */
    public void goodMethod() {
    }

    // violation 2 lines below 'Use snippet inline tag instead of 'code' tag.'
    /**
     * <code>
     *      List&lt;Integer&gt; list = new ArrayList&lt;&gt;();
     * </code>
     */
    public void badMethod1() {
    }

    /**
     * {@snippet :
     *      List<String> list = new ArrayList<>();
     * }
     */
    public void goodMethod1() {
    }

    /**
     * This is not flagged due to void element.
     * <code/>
     */
    public void method2() {
    }

    // violation 2 lines below 'Use snippet inline tag instead of 'code' tag.'
    /**
     * <code>
     *  There is some text.</code>
     *
     * <code> There is also text.
     * </code>
     * <p>No error for p tag</p>
     */
    public void method3() {
    // violation 5 lines above 'Use snippet inline tag instead of 'code' tag.'
    }

    // violation 2 lines below 'Use code or snippet inline tags instead of 'code' tag.'
    /**
     * <code></code>
     */
    public void method4() {
    }

}
