/*
ModifierOrder
modifierOrder = public,protected,private,static,final,abstract,transient, \
                volatile,default,synchronized,native,strictfp

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

/**
 * Test case for ModifierOrder check with custom modifier order.
 * Custom order: public, protected, private, static, final, abstract,
 *               transient, volatile, default, synchronized, native, strictfp
 * Note: In this custom order, static comes before final (like OpenJDK),
 *       and abstract comes after final.
 */
final strictfp class InputModifierOrderCustom {

    /** Correct order according to custom style: private static final */
    private static final String CORRECT_ORDER = "correct";

    /** Correct order: public static final */
    public static final int MAX_VALUE = 100;

    /**
     * Correct order for methods: private static final
     */
    private static final void correctMethod() {
    }

    /**
     * Correct order: private abstract
     * In this custom order, abstract comes after final
     */
    private abstract void abstractMethod();

    /**
     * Correct order: private transient
     */
    private transient String transientField;

    /**
     * Correct order: volatile field
     */
    private volatile boolean flag;

    /**
     * Correct order: synchronized method
     */
    private synchronized void synchronizedMethod() {
    }

    /**
     * Correct order: native method
     */
    private native void nativeMethod();

    /**
     * Correct order: strictfp class/method
     * strictfp comes last
     */
    private strictfp void strictfpMethod() {
    }

    /**
     * Violation: final before static (wrong for custom style)
     * In this custom order, static should come before final
     */
    private final static String WRONG_ORDER = "wrong"; // violation ''static'.*out of order.*'

    /**
     * Violation: final before static for method
     */
    private final static void wrongMethod() { // violation ''static'.*out of order.*'
    }
}
