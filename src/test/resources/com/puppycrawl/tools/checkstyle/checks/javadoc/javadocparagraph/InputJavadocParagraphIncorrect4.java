/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

/**
 * Some summary.
 *
 * <p><h1>Testing...</h1>
 */
// violation 2 lines above '<p> tag should not precede HTML block-tag '<h1>''
public class InputJavadocParagraphIncorrect4 {
    // violation 4 lines below '<p> tag should not precede HTML block-tag '<ul>''
    /**
     * Some summary.
     *
     *<p>
     *  <ul>
     *    <p>
     *      <li>1</li> should NOT give violation as there is not empty line before
     *  </ul>
     *
     *
     * <p><b>testing</b> ok, inline HTML tag. Not a block-level tag
     */
    public void foo() {}

    // violation 4 lines below '<p> tag should not precede HTML block-tag '<table>''
    /**
     *  Some summary.
     *
     * <p>
     *  <table>
     *  </table>
     *
     * <p>This is allowed<h1>Testing....</h1>
     */
    public void fooo() {}

    // violation 4 lines below '<p> tag should be placed immediately before the first word'
    /**
     * <h1>Testing....</h1>
     *
     * <p>     Test<h1>test</h1>
     *
     * <p>    <ol>test</ol>
     *
     * <p><b><h1>Nesting....</h1></b>
     */
    // 2 violations 4 lines above:
    // '<p> tag should be placed immediately before the first word'
    // '<p> tag should not precede HTML block-tag '<ol>''
    void foooo() {}
}
