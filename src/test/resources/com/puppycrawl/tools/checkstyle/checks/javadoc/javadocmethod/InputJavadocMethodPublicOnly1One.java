/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers =
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodPublicOnly1One // ignore - need javadoc
{
    private interface InnerInterface // ignore - when not relaxed about Javadoc
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
                        public void run() {};
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

    private InputJavadocMethodPublicOnly1One(int aA)
    {
    }

    // ignore - need Javadoc when not relaxed
    InputJavadocMethodPublicOnly1One(String aA)
    {
    }

    // ignore - always need javadoc

    protected InputJavadocMethodPublicOnly1One(Object aA)
    {
    }

    // ignore - always need javadoc
    public InputJavadocMethodPublicOnly1One(Class<Object> aA)
    {
    }

    /** Here should be an error, In scope */
    private void method(int aA)
    {
    }

    /** Here should be an error, In scope */
    void method(Long aA)
    {
    }

    /** Here should be an error, In scope */
    protected void method(Class<Object> aA)
    {
    }

    /** Here should be an error, In scope */
    public void method(StringBuffer aA)
    {
    }


    /**
       A param tag should not be required here when relaxed about Javadoc.
       Writing a little documentation should not be worse than not
       writing any documentation at all.
     */
    private void method(String aA)
    {
    }

    /**
       This inner class has no author tag, which is OK.
     */
    public class InnerWithoutAuthor
    {

    }

}
