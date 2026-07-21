/*
JavadocBlockTagLocation
tags = apiNote,implSpec, implNote
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

public class InputJavadocBlockTagLocationCustomTags {

    // 3 violations 8 lines below:
    //     'The Javadoc block tag '@apiNote' should be placed'
    //     'The Javadoc block tag '@implNote' should be placed'
    //     'The Javadoc block tag '@implSpec' should be placed'
    // violation 6 lines below 'The Javadoc block tag '@apiNote' should be placed'
    // violation 6 lines below 'The Javadoc block tag '@implNote' should be placed'
    // violation 6 lines below 'The Javadoc block tag '@implSpec' should be placed'
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
