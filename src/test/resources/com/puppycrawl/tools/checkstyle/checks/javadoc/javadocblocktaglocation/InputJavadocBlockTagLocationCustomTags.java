/*
JavadocBlockTagLocation
tags = apiNote,implSpec, implNote
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

public class InputJavadocBlockTagLocationCustomTags {

    // violation 7 lines below ''@apiNote' should be placed at the beginning'
    // violation 6 lines below ''@implNote' should be placed at the beginning'
    // violation 5 lines below ''@implSpec' should be placed at the beginning'
    // violation 6 lines below ''@apiNote' should be placed at the beginning'
    // violation 6 lines below ''@implNote' should be placed at the beginning'
    // violation 6 lines below ''@implSpec' should be placed at the beginning'
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
