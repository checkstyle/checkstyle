package com.puppycrawl.tools.checkstyle.javadocpropertiesgenerator;

public class InputJavadocPropertiesGeneratorGetFirstJavadocSentence {

    // Test getFirstJavadocSentence with SingleLineComment above javadoc.
    // This test case will be helpful to test the loop.
    // We can ensure that we print the correct javadoc no matter how many
    // tokens are as a first child of MODIFIERS.
    /**
     * First Javadoc Sentence.
     */
    public static final int EOF1 = 1;
}
