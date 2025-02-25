/*
RedundantModifier
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE
jdkVersion = 11

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

/**
 * Test case for Modifier checks:
 * - order of modifiers
 * - use of 'public' in interface definition
 * @author lkuehne
 */
strictfp final class InputRedundantModifierItTwo {
}
/** Holder for redundant modifiers of annotation fields/variables */
@interface Annotation
{
    public String s1 = ""; // violation
    final String s2 = ""; // violation
    static String s3 = ""; // violation
    String s4 = "";
    public String blah(); // violation
    abstract String blah2(); // violation
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
/** holder for interface specific modifier check. */
interface InputDefaultPublicModifier
{
    /** correct order */
    default strictfp void a()
    {
    }

    /** wrong order */
    strictfp default void b()
    {
    }
}
