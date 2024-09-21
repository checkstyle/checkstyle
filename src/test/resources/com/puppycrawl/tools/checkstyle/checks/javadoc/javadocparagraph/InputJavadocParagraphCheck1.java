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
     *  <ul>
     *    <p>
     *      <li>1</li> should NOT give violation as there is not empty line before
     *    </p>
     *  </ul>
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
}
