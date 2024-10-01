/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

public class InputJavadocParagraphIncorrect5 {
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
    // 2 violations 5 lines above:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should be preceded with an empty line.'
    void fooooo() {}

    // violation 4 lines below 'tag should be preceded with an empty line.'
    // violation 7 lines below 'tag should be preceded with an empty line.'
    // violation 12 lines below 'tag should be preceded with an empty line.'
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
     * <p> test...
     *
     * <h1></h1>
     *
     * <p>
     *
     * <h1></h1>
     */
    // violation 8 lines above '<p> tag should be placed immediately before the first word'
    // violation 5 lines above '<p> tag should be placed immediately before the first word'
    // violation 4 lines above 'block-level tag '<h1>' is preceded by <p> tag'
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
