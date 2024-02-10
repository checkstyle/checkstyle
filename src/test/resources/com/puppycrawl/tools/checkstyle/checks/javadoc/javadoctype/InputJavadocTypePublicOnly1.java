/*
JavadocType
scope = protected
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

public class InputJavadocTypePublicOnly1
{
    private interface InnerInterface
    {
        String CONST = "InnerInterface";
        void method();

        class InnerInnerClass
        {
            private int mData;

            private InnerInnerClass()
            {
                final Runnable r = new Runnable() {
                        public void run() {};
                    };
            }

            void method2()
            {
                final Runnable r = new Runnable() {
                        public void run() {};
                    };
            }
        }
    }

    private class InnerClass
    {
        private int mDiff;

        void method()
        {
        }
    }

    private int mSize;
    int mLen;
    protected int mDeer;
    public int aFreddo;


    private InputJavadocTypePublicOnly1(int aA)
    {
    }


    InputJavadocTypePublicOnly1(String aA)
    {
    }


    protected InputJavadocTypePublicOnly1(Object aA)
    {
    }


    public InputJavadocTypePublicOnly1(Class<Object> aA)
    {
    }


    private void method(int aA)
    {
    }


    void method(Long aA)
    {
    }


    protected void method(Class<Object> aA)
    {
    }


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

    /** {@inheritDoc} */
    public String toString()
    {
        return super.toString();
    }

    @Deprecated @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
