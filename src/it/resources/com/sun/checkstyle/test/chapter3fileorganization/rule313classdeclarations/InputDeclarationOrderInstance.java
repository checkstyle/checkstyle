package com.sun.checkstyle.test.chapter3fileorganization.rule313classdeclarations;

/**
 * Test input for declaration order with instance variable order violation.
 */
public final class InputDeclarationOrderInstance {

    /** Static constant. */
    public static final int CONST = 1;

    /**
     * Constructor.
     */
    public InputDeclarationOrderInstance() {
        instanceVar = 0;
    }

    /**
     * Gets value.
     *
     * @return instance variable value
     */
    public int getValue() {
        return instanceVar;
    }


    /** Instance variable after method. */
    private int instanceVar;
    // violation above 'Instance variable definition in wrong order.'
}
