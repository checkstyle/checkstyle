package com.sun.checkstyle.test.chapter3fileorganization.rule313classdeclarations;

/**
 * Test input for declaration order with multiple violations.
 */
public final class InputDeclarationOrderMultipleViolations {

    /** Package-private static constant. */
    static final int PACKAGE_CONST = 1;

    /** Private static constant. */
    private static final int PRIVATE_CONST = 2;

    /** Public after private. */
    // violation below 'Variable access definition in wrong order.'
    public static final int PUBLIC_CONST = 3;

    /** Instance variable. */
    private int instanceVar;

    /**
     * Gets value.
     *
     * @return calculated value
     */
    public int getValue() {
        return PACKAGE_CONST + PRIVATE_CONST + PUBLIC_CONST + instanceVar
                + staticVar;
    }

    /**
     * Constructor.
     */ // violation below 'Constructor definition in wrong order.'
    public InputDeclarationOrderMultipleViolations() {
        instanceVar = 0;
    }


    /** Static variable after method. */
    private static int staticVar = 0;
    // violation above 'Static variable definition in wrong order.'
}
