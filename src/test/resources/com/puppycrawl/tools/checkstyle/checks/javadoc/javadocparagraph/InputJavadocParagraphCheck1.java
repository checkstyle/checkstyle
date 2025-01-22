/*
JavadocParagraph

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

public class InputJavadocParagraphCheck1 {

}


/**
 * Input based test.
 *
 * <p>Check.
 *

 * <p>CheckStyle.
 *
 * @author Kevin
 */
// violation 4 lines above '<p> tag should be preceded with an empty line'
class Check {

   // violation 2 lines below 'Redundant <p> tag.'
   /**
    * <p>
    * Checks whether file contains code. Files which are considered to have no code:
    * </p>
    * <p>
    */
   // violation 2 lines above '<p> tag should be preceded with an empty line.'
    void inheritDocWithThrows() {}

    /**
     * Some summary.
     *
     * <p>The following package declaration:</p>
     * <pre>
     * package com.puppycrawl.tools.checkstyle;
     * </pre>
     */
    int iamtoolazyyyyyyy = 45;

    /**
     * Some summary.
     *
     *<p>
     *  <ul> // ok until #15762
     *    <p> // ok until #15762
     *      <li>1</li> should NOT give violation as there is not empty line before
     *    </p> // false-negative on this line & above until #15762
     *  </ul> // ok until #15762
     *</p>
     */
    public void foo() {}
    // 2 violations 9 lines above:
    //                            '<p> tag should not be succeeded by spaces before the first word'
    //                            '<p> tag should not precede HTML block-tag '<ul>''

    /**
     * Some summary.
     *
     * <b>
     * <p>
     *  <br/>
     * </p>
     * </b>
     */
    public void fooo() {}
    // 2 violations 6 lines above:
    //                            '<p> tag should be preceded with an empty line.'
    //                            '<p> tag should not be succeeded by spaces before the first word'

    /**
     *  Some summary.
     *
     * <p>
     *  <table> // ok until #15762
     *  </table> // ok until #15762
     * </p>
     */
    public void foooo() {}
    // 2 violations 6 lines above:
    //                            '<p> tag should not be succeeded by spaces before the first word'
    //                            '<p> tag should not precede HTML block-tag '<table>''

    /**
     * Some summary.
     *
     * <p>
     *   <pre>testing...</pre> // ok until #15762
     *   <pre>testing...</pre> // ok until #15762
     * </p>
     */
    public void fooooo() {}
    // 2 violations 6 lines above:
    //                            '<p> tag should not be succeeded by spaces before the first word'
    //                            '<p> tag should not precede HTML block-tag '<pre>''

}
