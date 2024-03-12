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

   /**
    * <p>
    * Checks whether file contains code. Files which are considered to have no code:
    * </p>
    * <p>
    */ // 2 violations above
    void inheritDocWithThrows() {}
}
