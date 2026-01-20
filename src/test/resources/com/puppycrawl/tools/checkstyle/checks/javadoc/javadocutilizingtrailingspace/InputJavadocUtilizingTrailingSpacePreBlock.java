/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for content inside pre blocks (should be completely ignored).
 */
public class InputJavadocUtilizingTrailingSpacePreBlock {

    /**
     * Example usage:
     * <pre>
     * This is a very very very very very long line inside a pre block that exceeds the limit but should be ignored.
     * short
     * line
     * </pre>
     */
    public void preBlockContent() { }

    /**
     * Code example:
     * <pre>
     * public void method() {
     *     // This extremely long comment exceeds the limit but is inside pre so ignored
     *     doSomething();
     * }
     * </pre>
     */
    public void codeInPre() { }

    /**
     * <pre>
     * Entire javadoc is a pre block with very long lines that would normally violate but are ignored completely.
     * </pre>
     */
    public void onlyPreBlock() { }

    /**
     * Before pre block.
     * <pre>
     * Inside pre block - lines here can be any length without triggering violations at all.
     * </pre>
     * After pre block resumes normal checking rules.
     */
    public void mixedPreAndNormal() { }

    /**
     * Nested formatting:
     * <pre>
     *   <code>
     *     Very long line of code that would exceed the limit but is inside pre so it is fine.
     *   </code>
     * </pre>
     */
    public void preWithCode() { }

    /**
     * <pre>short</pre>
     */
    public void shortPreInline() { }

    /**
     * <pre>
     * Line1
     * Line2
     * Line3 that is also short
     * </pre>
     */
    public void normalPreContent() { }
}
