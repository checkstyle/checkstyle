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
    
    // violation 4 lines below '<p> tag should be preceded with an empty line.'
    // violation 4 lines below 'Empty line should be followed by <p> tag on the next line.'
    /**
     * Version 1.8 GLOBAL TEMPORARY table definitions persist beyond the end
     * of the session (by default, data is cleared at commit).<p>
     *
     * Version 2.x LOCAL TEMPORARY table definitions do not persist beyond
     */
     public boolean test() { }
}
