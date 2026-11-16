/*
JavadocParagraph
allowNewlineParagraph = (default)true
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

public class InputJavadocParagraphCheck1 {

}


// violation 7 lines below '<p> tag should be preceded with an empty line'
/**
 * Input based test.
 *
 * <p>Check.
 *

 * <p>CheckStyle.
 *
 * @author Kevin
 */
class Check {

   // violation 3 lines below 'Redundant <p> tag.'
   // violation 5 lines below '<p> tag should be preceded with an empty line.'
   /**
    * <p>
    * Checks whether file contains code. Files which are considered to have no code:
    * </p>
    * <p>
    */
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
     *  <ul>
     *    <p>
     *      <li>1</li> should NOT give violation as there is not empty line before
     *    </p>
     *  </ul>
     *</p>
     */
    public void foo() {}


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
     *  <table>
     *  </table>
     * </p>
     */
    public void foooo() {}

    // violation 4 lines below '<p> tag should not precede HTML block-tag '<pre>''
    /**
     * Some summary.
     *
     * <p>
     *   <pre>testing...</pre>
     *   <pre>testing...</pre>
     * </p>
     */
    public void fooooo() {}

     /**
     * For the example above Checkstyle will enforce:
     *
     * <pre>
     * if
     * </pre>
     *
     **/
    void test() { }

    /**
     * {@code
     * 
     *   Test
     * }
     */
    void test2()  {
    }
}
