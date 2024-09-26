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
// violation 2 lines above 'block-level tag '<h1>' is preceded by <p> tag'
public class InputJavadocParagraphIncorrect3 {
    // violation 5 lines below 'block-level tag '<ul>' is preceded by <p> tag'
    /**
     * Some summary.
     *
     *<p>
     *  <ul>
     *    <p>
     *      <li>1</li> // should NOT give violation as there is not empty line before
     *  </ul>
     *
     *
     * <p><b>testing</b> // ok, inline HTML tag. Not a block-level tag
     */
    // violation 7 lines above '<p> tag should be preceded with an empty line.'
    public void foo() {}

    // violation 5 lines below 'block-level tag '<table>' is preceded by <p> tag'
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


    // violation 4 lines below 'tag should be placed immediately before the first word'
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
    //  '<p> tag should be placed immediately before the first word'
    //  'block-level tag '<ol>' is preceded by <p> tag, preceding <p> tag should be removed.'
    void foooo() {}

    // violation 3 lines below 'tag should be preceded with an empty line.'
    // violation 4 lines below 'tag should be preceded with an empty line.'
    /**
     * <h1><p>Testing....</h1>
     *
     * <h6><b><p>Test</b></h6>
     *
     * <table>
     *  <p>
     *    test
     *
     * </table>
     */
    // violation 5 lines above '<p> tag should be preceded with an empty line.'
    void fooooo() {}

    // violation 3 lines below 'tag should be preceded with an empty line.'
    // violation 6 lines below '<p> tag should be preceded with an empty line.'
    /**
     * <b><p>testtttt.....</b>
     *
     * <b>
     *
     * <p>testtttt.....
     *
     * </b>
     *
     * <h1>
     *
     * <p>testtttt.....
     *
     * </h1>
     *
     * <p>
     *
     * <h1></h1>
     *
     * <p>
     */
    // violation 10 lines above '<p> tag should be preceded with an empty line.'
    // violation 5 lines above 'block-level tag '<h1>' is preceded by <p> tag'
    void foooooo() {}

    /**
     * <ul>
     *  <li>1</li>
     *  <li>1</li>
     *  <ul>
     *    <p>test
     *  </ul>
     * </ul>
     */
    // violation 4 lines above 'tag should be preceded with an empty line.'
    void fooooooo() {}
}
