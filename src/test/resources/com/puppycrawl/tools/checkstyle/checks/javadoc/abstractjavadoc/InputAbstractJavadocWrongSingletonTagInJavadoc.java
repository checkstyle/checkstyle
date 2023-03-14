package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/*
 * Config: TempCheck
 */

public class InputAbstractJavadocWrongSingletonTagInJavadoc {
    /**
     * </embed> // violation
     */
    private int field1;

    /**
     * </keygen> // violation
     */
    private int field2;

    /**
     * </SOURCE> // violation
     */
    private int field3;

    /**
     * </TRACK> // violation
     */
    private int field4;

    /**
     * </WBR> // violation
     */
    private int field5;
}
