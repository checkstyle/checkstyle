/*
ModifierOrder


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

/**
 * Test case for Modifier checks:
 * - order of modifiers
 * - use of 'public' in interface definition
 * @author lkuehne
 */
strictfp final class InputModifierOrderItTwo { // violation ''final'.*out of order.*JLS.'

}
class SafeVarargsUsage {
    @Deprecated
    @SafeVarargs
    private final void foo(int... k) {}

    @Deprecated
    @SafeVarargs
    @SuppressWarnings("")
    private final void foo1(Object... obj) {}
}
enum TestEnum {
    ;

    public void method() {
    }
}
/** Holder for redundant 'final' check. */
final class RedundantFinalClass
{
    /** redundant 'final' modifier */
    public final void finalMethod()
    {
    }

    /** OK */
    public void method()
    {
    }
}

/** holder for interface specific modifier check. */
interface InputDefaultPublicModifier
{
    /** correct order */
    default strictfp void a()
    {
    }

    /** wrong order */
    strictfp default void b() // violation ''default' modifier out of order .*JLS suggestions.'
    {
    }
}
