/*
JavadocParagraph
allowNewlineParagraph = (default)true
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

public class InputJavadocParagraphIncorrect6 {
    // violation 4 lines below '<p> tag should be placed immediately before the first word'
    /**
    * Some Summary.
    *
    * <p><!-- comment is required --> extra space in paragraph after comment is violation
    */
    int a;

    // violation 4 lines below '<p> tag should be placed immediately before the first word'
    /**
    * Some Summary.
    *
    * <p> <!-- comment is required -->extra space in paragraph before comment is violation
    */
    int b;

    // violation 4 lines below '<p> tag should be placed immediately before the first word'
    /**
    * Some Summary.
    *
    * <p> <!-- comment is required --> both cases combined, this is also violation
    */
    int c;

    /**
    * Some Summary.
    *
    * <p><!-- comment is required -->no extra spaces, this is okay
    */
    int d;
}
