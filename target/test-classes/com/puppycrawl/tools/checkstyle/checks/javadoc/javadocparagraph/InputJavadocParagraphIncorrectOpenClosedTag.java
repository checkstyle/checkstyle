/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

/**
 * Some summary.
 *
 * <p>  Some Javadoc. </p>
 */
// violation 2 lines above '<p> tag should be placed immediately before the first word'
public class InputJavadocParagraphIncorrectOpenClosedTag {

    /**
    * some javadoc.
    * <p></p>
    */
    // violation 2 lines above '<p> tag should be preceded with an empty line.'
    int a;

    // violation 2 lines below '<p> tag should be preceded with an empty line.'
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
    *   Some Javadoc.
    * </p>
    */
    int d;

    // violation 4 lines below '<p> tag should not precede HTML block-tag '<ul>''
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

    /**
    * Some Summary.
    *
    * <p><b>testing....</b></p>
    *
    * <p><h1>testing....</h1></p>
    */
    // violation 2 lines above '<p> tag should not precede HTML block-tag '<h1>''
    int f;

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
    // violation 4 lines above '<p> tag should not precede HTML block-tag '<h1>''
    int g;
}
