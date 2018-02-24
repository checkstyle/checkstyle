package com.puppycrawl.tools.checkstyle.javadocpropertiesgenerator;

public final class InputJavadocPropertiesGeneratorNotConstants {

    /**
     * The end of file token.
     **/
    public final int EOF = 0; // must be static
    
    /**
     * Modifiers for type, method, and field declarations.
     **/
    static final int MODIFIERS = 1; // must be public

    /**
     * An object block.
     **/
    public static int OBJBLOCK = MODIFIERS; // must be final

    /**
     * A list of statements.
     **/
    public static final double SLIST = 3; // must be int

    /**
     * A static initialization block.
     **/
    public static final @Deprecated int[] STATIC_INIT = null; // must be int

    /**
     * Not a field.
     **/
    public static final int method() { // must be field
        return OBJBLOCK;
    }
}
