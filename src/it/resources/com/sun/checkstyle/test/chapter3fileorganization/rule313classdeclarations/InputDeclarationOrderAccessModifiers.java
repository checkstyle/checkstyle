package com.sun.checkstyle.test.chapter3fileorganization.rule313classdeclarations;

/**
 * Test input for declaration order with access modifier violations.
 */
public final class InputDeclarationOrderAccessModifiers {

    /** Package-private constant. */
    static final int PACKAGE_VAR = 1;

    /** Public after package. */
    // violation below 'Variable access definition in wrong order.'
    public static final int PUBLIC_VAR = 2;

    /** Private constant. */
    private static final int PRIVATE_VAR = 3;

    /** Protected after private. */
    // violation below 'Variable access definition in wrong order.'
    protected static final int PROTECTED_VAR = 4;

    /**
     * Gets value.
     *
     * @return constant value
     */
    public int getValue() {
        return PACKAGE_VAR + PUBLIC_VAR + PRIVATE_VAR + PROTECTED_VAR;
    }
}
