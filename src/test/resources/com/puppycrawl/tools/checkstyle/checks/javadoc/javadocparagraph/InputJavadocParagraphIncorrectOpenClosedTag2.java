/*
JavadocParagraph
allowNewlineParagraph = false
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

// violation 4 lines below '<p> tag should be placed immediately before the first word'
/**
 * Some summary.
 *
 * <p>  Some Javadoc. </p>
 */
public class InputJavadocParagraphIncorrectOpenClosedTag2 {

    // violation 3 lines below '<p> tag should be preceded with an empty line.'
    /**
    * some javadoc.
    * <p></p>
    */
    int a;

    // 2 violations 5 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should be preceded with an empty line.'
    // violation 3 lines below '<p> tag should be preceded with an empty line.'
    /**
    * Some Javadoc.<P>
    * Some Javadoc.<P></P>
    */
    int b;

    // violation 3 lines below 'Redundant <p> tag.'
    // violation 3 lines below 'Empty line should be followed by <p> tag on the next line.'
    // violation 4 lines below 'Empty line should be followed by <p> tag on the next line.'
    /**<p>some javadoc.</p>
    *
    * Some <p>Javadoc.</p>
    *
    * Some <p>Javadoc.</p>
    */
    int c;

    // violation 4 lines below '<p> tag should be placed immediately before the first word'
    /**
    * Some Summary.
    *
    * <p>
    *   Some Javadoc.
    * </p>
    */
    int d;

    // 2 violations 6 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should not precede HTML block-tag '<ul>''
    /**
    * Some Summary.
    *
    * <p>
    *   <ul>
    *     <li>Item 1</li>
    *     <li>Item 2</li>
    *     <li>Item 3</li>
    *   </ul>
    * </p>
    */
    int e;

    // violation 6 lines below '<p> tag should not precede HTML block-tag '<h1>''
    /**
    * Some Summary.
    *
    * <p><b>testing....</b></p>
    *
    * <p><h1>testing....</h1></p>
    */
    int f;

    // violation 7 lines below '<p> tag should be placed immediately before the first word'
    // 2 violations 10 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should not precede HTML block-tag '<h1>''
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
    int g;
}
