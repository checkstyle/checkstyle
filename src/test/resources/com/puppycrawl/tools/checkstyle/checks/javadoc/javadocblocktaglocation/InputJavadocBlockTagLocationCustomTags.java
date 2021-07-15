/*
JavadocBlockTagLocation
tags = apiNote, implSpec, implNote
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

public class InputJavadocBlockTagLocationCustomTags {

    /**
     * Text. @apiNote note @implNote note @implSpec spec // violation
     * {@code @apiNote}
     * text @apiNote note1 // violation
     * text text @implNote note2  // violation
     * text @implSpec  // violation
     * text @since 1.0
     */
    public void method(int param) {
    }

}
