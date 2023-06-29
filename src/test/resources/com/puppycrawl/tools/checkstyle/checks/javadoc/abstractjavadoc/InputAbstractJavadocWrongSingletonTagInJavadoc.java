package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/*
 * Config: TempCheck
 */

public class InputAbstractJavadocWrongSingletonTagInJavadoc {
    /**
     * </embed>
     */
    // violation above 'Javadoc comment at column 9 has parse error.
    // It is forbidden to close singleton HTML tags. Tag: embed.'
    private int field1;

    /**
     * </keygen>
     */
    // violation above 'Javadoc comment at column 9 has parse error.
    // It is forbidden to close singleton HTML tags. Tag: keygen.'
    private int field2;

    /**
     * </SOURCE>
     */
    // violation above 'Javadoc comment at column 9 has parse error.
    // It is forbidden to close singleton HTML tags. Tag: SOURCE.'
    private int field3;

    /**
     * </TRACK>
     */
    // violation above 'Javadoc comment at column 9 has parse error.
    // It is forbidden to close singleton HTML tags. Tag: TRACK.'
    private int field4;

    /**
     * </WBR>
     */
    // violation above 'Javadoc comment at column 9 has parse error.
    // It is forbidden to close singleton HTML tags. Tag: WBR.'
    private int field5;
}
