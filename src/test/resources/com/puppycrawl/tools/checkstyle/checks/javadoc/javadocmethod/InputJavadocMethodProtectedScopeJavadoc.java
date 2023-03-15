/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = public, protected
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodProtectedScopeJavadoc // ignore - need javadoc // ok
{
    private interface InnerInterface // ignore - when not relaxed about Javadoc // ok
    {
        String CONST = "InnerInterface"; // ignore - w.n.r.a.j
        void method(); // ignore - when not relaxed about Javadoc

        class InnerInnerClass // ignore - when not relaxed about Javadoc
        {
            private int mData; // ignore - when not relaxed about Javadoc

            private InnerInnerClass()
            {
                final Runnable r = new Runnable() {
                    public void run() {};
                };
            }

            void method2() // ignore - when not relaxed about Javadoc
            {
                final Runnable r = new Runnable() {
                    public void run() {}; // ok
                };
            }
        }
    }

    private class InnerClass // ignore
    {
        private int mDiff; // ignore - when not relaxed about Javadoc

        void method() // ignore - when not relaxed about Javadoc
        {
        }
    }

    private int mSize; // ignore - when not relaxed about Javadoc
    int mLen; // ignore - when not relaxed about Javadoc
    protected int mDeer; // ignore
    public int aFreddo; // ignore

    // ignore - need Javadoc
    private InputJavadocMethodProtectedScopeJavadoc(int aA) // ok
    {
    }

    // ignore - need Javadoc when not relaxed
    InputJavadocMethodProtectedScopeJavadoc(String aA) // ok
    {
    }

    // ignore - always need javadoc
    protected InputJavadocMethodProtectedScopeJavadoc(Object aA) // ok
    {
    }

    // ignore - always need javadoc
    public InputJavadocMethodProtectedScopeJavadoc(Class<Object> aA) // ok
    {
    }

    /** Here should not be an error, Out of scope */
    private void method(int aA) // ok
    {
    }

    /** Here should not be an error, Out of scope */
    void method(Long aA) // ok
    {
    }

    /** Here should be an error, In scope */
    protected void method(Class<Object> aA) // violation
    {
    }

    /** Here should be an error, In scope */
    public void method(StringBuffer aA) // violation
    {
    }


    /**
     A param tag should not be required here when relaxed about Javadoc.
     Writing a little documentation should not be worse than not
     writing any documentation at all.
     */
    private void method(String aA) // ok
    {
    }

    /**
     This inner class has no author tag, which is OK.
     */
    public class InnerWithoutAuthor // ok
    {

    }

    /** {@inheritDoc} */
    public String toString() // ok
    {
        return super.toString();
    }

    @Deprecated @Override
    public int hashCode() // ok
    {
        return super.hashCode();
    }
}
