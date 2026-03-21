/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

public class InputJavadocParagraphIncorrect6 {
    /**
    * Some Summary.
    *
    * <p><!-- comment is required --> extra space in paragraph after comment is violation
    */
    // violation 2 lines above '<p> tag should be placed immediately before the first word'
    int a;

    /**
    * Some Summary.
    *
    * <p> <!-- comment is required -->extra space in paragraph before comment is violation
    */
    // violation 2 lines above '<p> tag should be placed immediately before the first word'
    int b;

    /**
    * Some Summary.
    *
    * <p> <!-- comment is required --> both cases combined, this is also violation
    */
    // violation 2 lines above '<p> tag should be placed immediately before the first word'
    int c;

    /**
    * Some Summary.
    *
    * <p><!-- comment is required -->no extra spaces, this is okay
    */
    int d;
}
