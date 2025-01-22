/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

public class InputJavadocParagraphIndentation {

    /**
    * Some Summary.
    *
    * <p>
    *   Some Javadoc.
    * </p>
    */
    // violation 4 lines above '<p> tag should not be succeeded by spaces before the first word'
    int d;

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
    // 2 violations 8 lines above:
    //                            '<p> tag should not be succeeded by spaces before the first word'
    //                            '<p> tag should not precede HTML block-tag '<ul>', <p> tag should be removed'
    int e;
}
