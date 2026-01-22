package com.sun.checkstyle.test.chapter3fileorganization.rule313classdeclarations;

/**
 * Test input for valid declaration order with inner class.
 */
public final class InputDeclarationOrderValidInnerClass {

    /** Public static constant. */
    public static final int PUBLIC_CONST = 1;

    /** Protected static constant. */
    protected static final int PROTECTED_CONST = 2;

    /** Private static constant. */
    private static final int PRIVATE_CONST = 3;

    /** Instance variable. */
    private int instanceVar;

    /**
     * Constructor.
     */
    public InputDeclarationOrderValidInnerClass() {
        instanceVar = 0;
    }

    /**
     * Gets value.
     *
     * @return calculated value
     */
    public int getValue() {
        return PUBLIC_CONST + PROTECTED_CONST + PRIVATE_CONST + instanceVar;
    }

    /**
     * Valid inner class with proper declaration order.
     */
    private static final class InnerClass {

        /** Inner static constant. */
        private static final int INNER_CONST = 1;

        /** Inner instance variable. */
        private int innerVar;

        /**
         * Inner constructor.
         */
        InnerClass() {
            innerVar = 0;
        }

        /**
         * Gets inner value.
         *
         * @return inner value
         */
        int getInnerVar() {
            return innerVar + INNER_CONST;
        }
    }
}
