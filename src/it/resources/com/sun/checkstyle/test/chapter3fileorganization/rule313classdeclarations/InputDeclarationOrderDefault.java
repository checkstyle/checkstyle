package com.sun.checkstyle.test.chapter3fileorganization.rule313classdeclarations;

// violation first line 'Header mismatch'

/**
 * Test input for declaration order with default scenarios.
 */
public final class InputDeclarationOrderDefault {

    /** Static constant. */
    public static final int FOO = 1;

    /** Private constant. */
    private static final int BAR = 2;

    // violation 2 lines below 'Variable access definition in wrong order.'
    /** Public before private. */
    public static final int BAZ = 3;

    /** Instance variable. */
    private int instanceVar;

    /**
     * Constructor.
     */
    public InputDeclarationOrderDefault() {
        instanceVar = 0;
    }

    /**
     * Gets instance variable.
     *
     * @return instance variable value
     */
    public int getInstanceVar() {
        return instanceVar;
    }
}
