/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$TempCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

public class InputAbstractJavadocWrongSingletonTagInJavadoc {
    /**
     * </embed>
     */
    // violation 2 lines above
    private int field1;

    /**
     * </keygen>
     */
    // violation 2 lines above
    private int field2;

    /**
     * </SOURCE>
     */
    // violation 2 lines above
    private int field3;

    /**
     * </TRACK>
     */
    // violation 2 lines above
    private int field4;

    /**
     * </WBR>
     */
    // violation 2 lines above
    private int field5;
}
