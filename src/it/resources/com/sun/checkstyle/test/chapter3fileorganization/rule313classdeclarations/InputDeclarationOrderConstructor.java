package com.sun.checkstyle.test.chapter3fileorganization.rule313classdeclarations;

/**
 * Test input for declaration order with constructor order violation.
 */
public final class InputDeclarationOrderConstructor {

    /** Static constant. */
    public static final int CONST = 1;

    /** Instance variable. */
    private int instanceVar;

    /**
     * Gets value.
     *
     * @return instance variable value
     */
    public int getValue() {
        return instanceVar;
    }

    /**
     * Constructor after method.
     */ // violation below 'Constructor definition in wrong order.'
    public InputDeclarationOrderConstructor() {
        instanceVar = 0;
    }
}
