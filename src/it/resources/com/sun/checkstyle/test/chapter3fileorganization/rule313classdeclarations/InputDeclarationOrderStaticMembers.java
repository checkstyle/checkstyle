package com.sun.checkstyle.test.chapter3fileorganization.rule313classdeclarations;

/**
 * Test input for declaration order with static member violations.
 */
public final class InputDeclarationOrderStaticMembers {

    /** Instance variable first. */
    private int instanceVar = 1;

    /** Static after instance. */
    // violation below 'Static variable definition in wrong order.'
    public static final int STATIC_VAR = 2;

    /**
     * Constructor.
     */
    public InputDeclarationOrderStaticMembers() {
        instanceVar = 0;
    }

    /**
     * Gets value.
     *
     * @return instance variable value
     */
    public int getValue() {
        return instanceVar + STATIC_VAR;
    }
}
