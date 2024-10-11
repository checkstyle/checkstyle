/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

/**
 * Some summary.
 *
 * <p>  Some Javadoc. </p>
 */
// violation 2 lines above '<p> tag should be placed immediately before the first word'
public class InputJavadocParagraphIncorrectOpenClosedTag2 {

    /**
    * some javadoc.
    * <p></p>
    */
    // violation 2 lines above '<p> tag should be preceded with an empty line.'
    int a;

    // 2 violations 4 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should be preceded with an empty line.'
    /**
    * Some Javadoc.<P>
    * Some Javadoc.<P></P>
    */
    // violation 2 lines above '<p> tag should be preceded with an empty line.'
    int b;

    // violation below 'Redundant <p> tag.'
    /**<p>some javadoc.</p>
    *
    * Some <p>Javadoc.</p>
    *
    * Some <p>Javadoc.</p>
    */
    // violation 5 lines above 'Empty line should be followed by <p> tag on the next line.'
    // violation 4 lines above 'Empty line should be followed by <p> tag on the next line.'
    int c;

    /**
    * Some Summary.
    *
    * <p>
    *   Some Javadoc. // ok until #15762
    * </p>
    */
    // violation 4 lines above '<p> tag should be placed immediately before the first word'
    int d;

    // 2 violations 6 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should not precede HTML block-tag '<ul>''
    /**
    * Some Summary.
    *
    * <p>
    *   <ul> // ok until #15762
    *     <li>Item 1</li> // ok until #15762
    *     <li>Item 2</li> // ok until #15762
    *     <li>Item 3</li> // ok until #15762
    *   </ul> // ok until #15762
    * </p>
    */
    int e;

    /**
    * Some Summary.
    *
    * <p><b>testing....</b></p>
    *
    * <p><h1>testing....</h1></p>
    */
    // violation 2 lines above '<p> tag should not precede HTML block-tag '<h1>''
    int f;

    // violation 4 lines below '<p> tag should be placed immediately before the first word'
    /**
    * Some Summary.
    *
    * <p>
    *     <b>testing....</b>
    * </p>
    *
    * <p>
    *     <h1>testing....</h1>
    * </p>
    */
    // 2 violations 4 lines above:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should not precede HTML block-tag '<h1>''
    int g;
}
