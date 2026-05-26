/*
ModifierOrder
modifierOrder = openjdk

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

/**
 * Test case for ModifierOrder check with OpenJDK style modifier order.
 * OpenJDK order: public, protected, private, abstract, static, final,
 *                 transient, volatile, default, synchronized, native, strictfp
 * @see <a href="https://openjdk.org/projects/code-conventions/">OpenJDK Code Conventions</a>
 */
strictfp class InputModifierOrderOpenjdk {

    /** Correct order according to OpenJDK style: private static final */
    private static final String CORRECT_ORDER = "correct";

    /** Correct order: public static final */
    public static final int MAX_VALUE = 100;

    /** Correct order: protected static final */
    protected static final double PI = 3.14;

    /**
     * Correct order for methods: private static final
     * In OpenJDK style, static comes before final
     */
    private static final void correctMethod() {
    }

    /**
     * Correct order: public abstract
     * abstract comes after access modifier in OpenJDK style
     */
    public abstract void abstractMethod();

    /**
     * Correct order: private transient
     * transient comes after access modifier in OpenJDK style
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
     * strictfp comes last in OpenJDK style
     */
    private strictfp void strictfpMethod() {
    }

    /**
     * Violation: final before static (wrong for OpenJDK style)
     * In OpenJDK, static should come before final
     */
    private final static String WRONG_ORDER = "wrong"; // violation ''static'.*out of order.*'

    /**
     * Violation: final before static for method
     */
    private final static void wrongMethod() { // violation ''static'.*out of order.*'
    }
}
