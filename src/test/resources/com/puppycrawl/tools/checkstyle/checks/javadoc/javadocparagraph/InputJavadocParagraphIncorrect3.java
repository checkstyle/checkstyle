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
// violation 2 lines above 'block-level tag '\<h1\>' is preceded by \<p\> tag'
public class InputJavadocParagraphIncorrect3 {
    // violation 5 lines below 'tag should be placed immediately before the first word'
    // violation 5 lines below 'block-level tag '\<ul\>' is preceded by \<p\> tag'
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
    // 2 violations 7 lines above:
    //  '\<p\> tag should be placed immediately before the first word'
    //  '\<p\> tag should be preceded with an empty line.'
    public void foo() {}

    // violation 5 lines below 'tag should be placed immediately before the first word'
    // violation 5 lines below 'block-level tag '\<table\>' is preceded by \<p\> tag'
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
    //  '\<p\> tag should be placed immediately before the first word'
    //  'block-level tag '\<ol\>' is preceded by <p> tag, preceding \<p\> tag should be removed.'
    void foooo() {}

    /**
     * <h1><p>Testing....</h1> // violation 'tag should be preceded with an empty line.'
     *
     * <h6><b><p>Test</b></h6> // violation 'tag should be preceded with an empty line.'
     *
     * <table>
     *  <p>
     *    test
     *
     * </table>
     */
    // 2 violations 5 lines above:
    //  '\<p\> tag should be placed immediately before the first word'
    //  '\<p\> tag should be preceded with an empty line.'
    void fooooo() {}

    /**
     * <b><p>testtttt.....</b> // violation 'tag should be preceded with an empty line.'
     *
     * <b>
     *
     * <p>testtttt.....  // violation 'tag should be preceded with an empty line.'
     *
     * </b>
     *
     * <h1>
     *
     * <p>testtttt.....  // violation 'tag should be preceded with an empty line.'
     *
     * </h1>
     *
     * <p> // violation 'tag should be placed immediately before the first word'
     *
     * <h1></h1>
     *
     * <p> // violation 'tag should be placed immediately before the first word'
     */
    void foooooo() {}

    /**
     * <ul>
     *  <li>1</li>
     *  <li>1</li>
     *  <ul>
     *    <p>test // violation 'tag should be preceded with an empty line.'
     *  </ul>
     * </ul>
     */
    void fooooooo() {}
}
