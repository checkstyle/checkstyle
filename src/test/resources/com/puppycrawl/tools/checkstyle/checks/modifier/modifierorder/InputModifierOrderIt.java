////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

/**
 * Test case for Modifier checks:
 * - order of modifiers
 * - use of 'public' in interface definition
 * @author lkuehne
 */
strictfp final class InputModifierOrderIt // illegal order of modifiers for class
{

    /** Illegal order of modifiers for variables */
    static private boolean sModifierOrderVar = false;

    /**
     * Illegal order of modifiers for methods. Make sure that the
     * first and last modifier from the JLS sequence is used.
     */
    strictfp private void doStuff()
    {
    }

    /** Single annotation without other modifiers */
    @MyAnnotation2 void someMethod()
    {
    }

    /** Illegal order of annotation - must come first */
    private @MyAnnotation2 void someMethod2()
    {
    }

    /** Annotation in middle of other modifiers otherwise in correct order */
    private @MyAnnotation2 strictfp void someMethod3()
    {
    }

    /** Correct order */
    @MyAnnotation2 private strictfp void someMethod4()
    {
    }

    /** Annotation in middle of other modifiers otherwise in correct order */
    @MyAnnotation2 private static @MyAnnotation4 strictfp void someMethod5()
    {
    }

    /** holder for redundant 'public' modifier check. */
    public static interface InputRedundantPublicModifier // violation
    {
        /** redundant 'public' modifier */
        public void a(); // violation

        /** all OK */
        void b();

        /** redundant abstract modifier */
        abstract void c(); // violation

        /** redundant 'public' modifier */
        public float PI_PUBLIC = (float) 3.14; // violation

        /** redundant 'abstract' modifier (field can not be abstract) */
//        abstract float PI_ABSTRACT = (float) 3.14;

        /** redundant 'final' modifier */
        final float PI_FINAL = (float) 3.14; // violation

        /** all OK */
        float PI_OK = (float) 3.14;
    }

    /** redundant 'final' modifier */
    private final void method() // violation
    {
    }
}

/** Holder for redundant 'final' check. */
final class RedundantFinalClass
{
    /** redundant 'final' modifier */
    public final void finalMethod() // violation
    {
    }

    /** OK */
    public void method()
    {
    }
}

/** Holder for redundant modifiers of inner implementation */
abstract interface InnerImplementation // violation
{
    InnerImplementation inner =
            new InnerImplementation()
            {
                /** compiler requires 'public' modifier */
                public void method()
                {
                }
            };

    void method();
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

@interface MyAnnotation2 {
}

@interface MyAnnotation4 {
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
    strictfp default void b() // violation
    {
    }
}
