/*
JavadocBlockTagLocation
tags = apiNote,implSpec, implNote
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

public class InputJavadocBlockTagLocationCustomTags {

    // violation 7 lines below 'The Javadoc block tag '@apiNote' should be placed at the beginning of the line.'
    // violation 6 lines below 'The Javadoc block tag '@implNote' should be placed at the beginning of the line.'
    // violation 5 lines below 'The Javadoc block tag '@implSpec' should be placed at the beginning of the line.'
    // violation 6 lines below 'The Javadoc block tag '@apiNote' should be placed at the beginning of the line.'
    // violation 6 lines below 'The Javadoc block tag '@implNote' should be placed at the beginning of the line.'
    // violation 6 lines below 'The Javadoc block tag '@implSpec' should be placed at the beginning of the line.'
    /**
     * Text. @apiNote note @implNote note @implSpec spec
     * {@code @apiNote}
     * text @apiNote note1
     * text text @implNote note2
     * text @implSpec
     * text @since 1.0
     */
    public void method(int param) {
    }

}
