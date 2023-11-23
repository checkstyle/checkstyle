/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$TempCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

public class InputAbstractJavadocWrongSingletonTagInJavadoc {
    /**
     * </embed>
     */
    // violation 2 lines above 'Javadoc comment at column 9 has parse error.'
    // It is forbidden to close singleton HTML tags. Tag: embed.
    private int field1;

    /**
     * </keygen>
     */
    // violation 2 lines above 'Javadoc comment at column 9 has parse error.'
    // It is forbidden to close singleton HTML tags. Tag: keygen.
    private int field2;

    /**
     * </SOURCE>
     */
    // violation 2 lines above 'Javadoc comment at column 9 has parse error.'
    // It is forbidden to close singleton HTML tags. Tag: SOURCE.
    private int field3;

    /**
     * </TRACK>
     */
    // violation 2 lines above 'Javadoc comment at column 9 has parse error.'
    // It is forbidden to close singleton HTML tags. Tag: TRACK.
    private int field4;

    /**
     * </WBR>
     */
    // violation 2 lines above 'Javadoc comment at column 9 has parse error.'
    // It is forbidden to close singleton HTML tags. Tag: WBR.
    private int field5;
}
