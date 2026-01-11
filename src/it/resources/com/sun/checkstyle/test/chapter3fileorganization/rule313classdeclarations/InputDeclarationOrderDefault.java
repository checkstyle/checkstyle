package com.sun.checkstyle.test.chapter3fileorganization.rule313classdeclarations;

/**
 * Test input for declaration order with default scenarios.
 */
public final class InputDeclarationOrderDefault {

    /** Static constant. */
    public static final int FOO = 1;

    /** Private constant. */
    private static final int BAR = 2;

    /** Public before private. */
    // violation below 'Variable access definition in wrong order.'
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
