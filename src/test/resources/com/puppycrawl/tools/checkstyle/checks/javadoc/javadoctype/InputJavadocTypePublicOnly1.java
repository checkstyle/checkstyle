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

public class InputJavadocTypePublicOnly1 // ok
{
    private interface InnerInterface // ok
    {
        String CONST = "InnerInterface";
        void method();

        class InnerInnerClass // ok
        {
            private int mData;

            private InnerInnerClass()
            {
                final Runnable r = new Runnable() {
                        public void run() {};
                    };
            }

            void method2() // ok
            {
                final Runnable r = new Runnable() {
                        public void run() {};
                    };
            }
        }
    }

    private class InnerClass // ok
    {
        private int mDiff; // ok

        void method() // ok
        {
        }
    }

    private int mSize;
    int mLen;
    protected int mDeer;
    public int aFreddo;


    private InputJavadocTypePublicOnly1(int aA) // ok
    {
    }


    InputJavadocTypePublicOnly1(String aA) // ok
    {
    }


    protected InputJavadocTypePublicOnly1(Object aA)
    {
    }


    public InputJavadocTypePublicOnly1(Class<Object> aA)
    {
    }


    private void method(int aA) // ok
    {
    }


    void method(Long aA) // ok
    {
    }


    protected void method(Class<Object> aA) // ok
    {
    }


    public void method(StringBuffer aA) // ok
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
    public String toString()
    {
        return super.toString();
    } // ok

    @Deprecated @Override
    public int hashCode()
    {
        return super.hashCode();
    } // ok
}
