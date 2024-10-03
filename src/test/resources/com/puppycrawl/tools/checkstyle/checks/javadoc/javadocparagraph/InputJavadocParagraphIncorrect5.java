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
    // 2 violations 5 lines above:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should not precede HTML block-tag '<h1>''
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

    // 2 violations 6 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should not precede HTML block-tag '<ul>''
    /**
     * Some Summary.
     *
     * <p>
     *  <!-- THIS COMMENT WILL GET IGNORED -->
     *  <ul>
     *      <li>Item 1</li>
     *      <li>Item 2</li>
     *      <li>Item 3</li>
     *  </ul>
     */
    private static final String NAME = "Heisenberg";

    /**
     * Some Summary.
     *
     * <p>{@code com.company.MyClass$Nested#myMethod(String, int)}
     * <ul>
     *     <li>Item 1</li>
     *     <li>Item 2</li>
     *     <li>Item 3</li>
     * </ul>
     */
    private static final String NAME2 = "Jesse Pinkman";
}
