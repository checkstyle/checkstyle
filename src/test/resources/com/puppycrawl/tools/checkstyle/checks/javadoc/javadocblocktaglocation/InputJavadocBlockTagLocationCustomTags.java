package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

/**
 * configuration:
 * tags="apiNote, implSpec, implNote"
 */
public class InputJavadocBlockTagLocationCustomTags {

    /**
     * Text. @apiNote note @implNote note @implSpec spec //warn
     * {@code @apiNote}
     * text @apiNote note1 //warn
     * text text @implNote note2  //warn
     * text @implSpec  //warn
     * text @since 1.0
     */
    public void method(int param) {
    }

}
