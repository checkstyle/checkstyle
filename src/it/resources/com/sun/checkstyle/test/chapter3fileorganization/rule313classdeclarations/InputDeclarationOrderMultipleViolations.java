package com.sun.checkstyle.test.chapter3fileorganization.rule313classdeclarations;

// violation first line 'Header mismatch'

/**
 * Test input for declaration order with multiple violations.
 */
public final class InputDeclarationOrderMultipleViolations {

    /** Package-private static constant. */
    static final int PACKAGE_CONST = 1;

    /** Private static constant. */
    private static final int PRIVATE_CONST = 2;

    // violation 2 lines below 'Variable access definition in wrong order.'
    /** Public after private. */
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

    // violation 4 lines below 'Constructor definition in wrong order.'
    /**
     * Constructor.
     */
    public InputDeclarationOrderMultipleViolations() {
        instanceVar = 0;
    }


    /** Static variable after method. */
    private static int staticVar = 0;
    // violation above 'Static variable definition in wrong order.'
}
