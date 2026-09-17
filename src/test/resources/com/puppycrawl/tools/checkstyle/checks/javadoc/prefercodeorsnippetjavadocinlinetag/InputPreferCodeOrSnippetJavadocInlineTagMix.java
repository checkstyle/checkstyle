/*
PreferCodeOrSnippetJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.prefercodeorsnippetjavadocinlinetag;

public class InputPreferCodeOrSnippetJavadocInlineTagMix {

    // violation 2 lines below "Use snippet inline tag instead of 'pre' tag."
    /**
     * <pre>
     *      <pre>Nested pre.</pre>
     * </pre>
     */
    public void badMethod() {
    }

    /**
     * {@snippet :
     *      Nested pre.
     * }
     */
    public void goodMethod() {
    }

    // violation 2 lines below "Use snippet inline tag instead of 'pre' tag."
    /**
     * <pre>
     *      <code>Nested code in pre tag.</code>
     * </pre>
     */
    public void badMethod1() {
    }

    /**
     * {@snippet :
     *      Nested code in pre tag.
     * }
     */
    public void goodMethod1() {
    }

    // violation 2 lines below "Use snippet inline tag instead of 'code' tag."
    /**
     * <code>
     *      <code>Nested code.</code>
     * </code>
     */
    public void badMethod2() {
    }

    /**
     * {@snippet :
     *      Nested code.
     * }
     */
    public void goodMethod2() {
    }

    // violation 2 lines below "Use snippet inline tag instead of 'pre' tag."
    /**
     * <pre>
     *      class Solution {
     *             System.out.println("hello");
     *      }
     * </pre>
     */
    public void badMethod3() {
    }

}
