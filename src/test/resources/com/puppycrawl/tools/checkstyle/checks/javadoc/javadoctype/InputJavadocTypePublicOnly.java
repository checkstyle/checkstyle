/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

public class InputJavadocTypePublicOnly
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

    private InputJavadocTypePublicOnly(int aA)
    {
    }

    // ignore - need Javadoc when not relaxed
    InputJavadocTypePublicOnly(String aA)
    {
    }

    // ignore - always need javadoc
    protected InputJavadocTypePublicOnly(Object aA)
    {
    }

    // ignore - always need javadoc
    public InputJavadocTypePublicOnly(Class<Object> aA)
    {
    }

    // ignore - when not relaxed about Javadoc
    private void method(int aA)
    {
    }

    // ignore - when not relaxed about Javadoc
    void method(Long aA)
    {
    }

    // ignore - need javadoc
    protected void method(Class<Object> aA)
    {
    }

    // ignore - need javadoc
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
