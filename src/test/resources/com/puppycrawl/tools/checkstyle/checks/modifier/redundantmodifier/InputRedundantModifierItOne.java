/*
RedundantModifier
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA
jdkVersion = 11

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

/**
 * Test case for Modifier checks:
 * - order of modifiers
 * - use of 'public' in interface definition
 * @author lkuehne
 */
strictfp final class InputRedundantModifierItOne // illegal order of modifiers for class
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

    // violation 2 lines below 'Redundant 'static' modifier.'
    /** holder for redundant 'public' modifier check. */
    public static interface InputRedundantPublicModifier
    {
        /** redundant 'public' modifier */
        public void a(); // violation 'Redundant 'public' modifier.'

        /** all OK */
        void b();

        /** redundant abstract modifier */
        abstract void c(); // violation 'Redundant 'abstract' modifier.'

        // violation 2 lines below 'Redundant 'public' modifier.'
        /** redundant 'public' modifier */
        public float PI_PUBLIC = (float) 3.14;

        // redundant 'abstract' modifier (field can not be abstract)
//        abstract float PI_ABSTRACT = (float) 3.14;

        // violation 2 lines below 'Redundant 'final' modifier.'
        /** redundant 'final' modifier */
        final float PI_FINAL = (float) 3.14;

        /** all OK */
        float PI_OK = (float) 3.14;
    }

    /** redundant 'final' modifier */
    private final void method() // violation 'Redundant 'final' modifier.'
    {
    }
}

/** Holder for redundant 'final' check. */
final class RedundantFinalClass
{
    /** redundant 'final' modifier */
    public final void finalMethod() // violation 'Redundant 'final' modifier.'
    {
    }

    /** OK */
    public void method() {}
}

// violation 2 lines below 'Redundant 'abstract' modifier.'
/** Holder for redundant modifiers of inner implementation */
abstract interface InnerImplementation {
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
@interface MyAnnotation2 {}

@interface MyAnnotation4 {}
