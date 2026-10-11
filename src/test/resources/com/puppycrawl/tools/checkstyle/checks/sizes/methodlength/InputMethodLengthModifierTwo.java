/*
MethodLength
max = (default)150
countEmpty = (default)true
tokens = (default)METHOD_DEF , CTOR_DEF , COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

strictfp final class InputMethodLengthModifierTwo {}

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

class SafeVarargsUsage {
    @Deprecated
    @SafeVarargs
    private final void foo(int... k) {}

    @Deprecated
    @SafeVarargs
    @SuppressWarnings("")
    private final void foo1(Object... obj) {}
}
