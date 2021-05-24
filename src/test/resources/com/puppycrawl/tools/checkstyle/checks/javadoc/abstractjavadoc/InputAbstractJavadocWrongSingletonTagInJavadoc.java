package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

public class InputAbstractJavadocWrongSingletonTagInJavadoc {
    /**
     * </embed>
     */
    private int field1;

    /**
     * </keygen>
     */
    private int field2;

    /**
     * </SOURCE>
     */
    private int field3;

    /**
     * </TRACK>
     */
    private int field4;

    /**
     * </WBR>
     */
    private int field5;
}
