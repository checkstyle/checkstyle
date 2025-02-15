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
strictfp final class InputModifierOrderItOne // violation ''final'.*out of order.*JLS suggestions.'
{

    /** Illegal order of modifiers for variables */
    static private boolean sModifierOrderVar = false; // violation ''private'.*JLS suggestions.'

    /**
     * Illegal order of modifiers for methods. Make sure that the
     * first and last modifier from the JLS sequence is used.
     */
    strictfp private void doStuff() // violation ''private' modifier.*order.*JLS suggestions.'
    {
    }

    /** Single annotation without other modifiers */
    @MyAnnotation2 void someMethod()
    {
    }

    /** Illegal order of annotation - must come first */
    private @MyAnnotation2 void someMethod2()
    { // violation above ''@MyAnnotation2' annotation modifier.*precede non-annotation modifiers.'
    }

    /** Annotation in middle of other modifiers otherwise in correct order */
    private @MyAnnotation2 strictfp void someMethod3()
    { // violation above ''@MyAnnotation2' annotation modifier.*precede non-annotation modifiers.'
    }

    /** Correct order */
    @MyAnnotation2 private strictfp void someMethod4()
    {
    }

    /** Annotation in middle of other modifiers otherwise in correct order */
    @MyAnnotation2 private static @MyAnnotation4 strictfp void someMethod5()
    { // violation above ''@MyAnnotation4' annotation modifier.*precede non-annotation modifiers.'
    }

    /** holder for redundant 'public' modifier check. */
    public static interface InputRedundantPublicModifier
    {
        /** redundant 'public' modifier */
        public void a();

        /** all OK */
        void b();

        /** redundant abstract modifier */
        abstract void c();

        /** redundant 'public' modifier */
        public float PI_PUBLIC = (float) 3.14;

        /** redundant 'abstract' modifier (field can not be abstract) */
//        abstract float PI_ABSTRACT = (float) 3.14;

        /** redundant 'final' modifier */
        final float PI_FINAL = (float) 3.14;

        /** all OK */
        float PI_OK = (float) 3.14;
    }

    /** redundant 'final' modifier */
    private final void method()
    {
    }
}



/** Holder for redundant modifiers of inner implementation */
abstract interface InnerImplementation
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
    public String s1 = "";
    final String s2 = "";
    static String s3 = "";
    String s4 = "";
    public String blah();
    abstract String blah2();
}

@interface MyAnnotation2 {
}

@interface MyAnnotation4 {
}
