package com.sun.checkstyle.test.chapter3fileorganization.rule313classdeclarations;

/**
 * Test input for declaration order with valid order (no violations).
 */
public final class InputDeclarationOrderValid {

    /** Public static constant. */
    public static final int PUBLIC_CONST = 1;

    /** Protected static constant. */
    protected static final int PROTECTED_CONST = 2;

    /** Package-private static constant. */
    static final int PACKAGE_CONST = 3;

    /** Private static constant. */
    private static final int PRIVATE_CONST = 4;

    /** Instance variable. */
    private int instanceVar;

    /**
     * Constructor.
     */
    public InputDeclarationOrderValid() {
        instanceVar = 0;
    }

    /**
     * Gets value.
     *
     * @return calculated value
     */
    public int getValue() {
        return PUBLIC_CONST + PROTECTED_CONST + PACKAGE_CONST + PRIVATE_CONST
                + instanceVar;
    }
}
