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

    // violation 4 lines below '<p> tag should not precede HTML block-tag '<ul>''
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

    // violation 5 lines below '<p> tag should be preceded with an empty line.'
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

    // violation 4 lines below '<p> tag should not precede HTML block-tag '<table>''
    /**
     *  Some summary.
     *
     * <p>
     *  <table> // ok until #15762
     *  </table> // ok until #15762
     * </p>
     */
    public void foooo() {}

    // violation 4 lines below '<p> tag should not precede HTML block-tag '<pre>''
    /**
     * Some summary.
     *
     * <p>
     *   <pre>testing...</pre> // ok until #15762
     *   <pre>testing...</pre> // ok until #15762
     * </p>
     */
    public void fooooo() {}
}
