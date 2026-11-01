package com.puppycrawl.tools.checkstyle.javaparser;

public class InputJavaParserUnicodeEscapesInLiteralsAndComments {

    String text = "a\u0061b";

    char letter = '\u0061';

    // single line comment with \u0061 escape in it
    /* block comment with \u0062 escape in it */
    int plain = 1;
}
