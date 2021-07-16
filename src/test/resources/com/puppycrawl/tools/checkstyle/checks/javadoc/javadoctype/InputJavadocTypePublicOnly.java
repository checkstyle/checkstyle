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

public class InputJavadocTypePublicOnly // ok
{
    private interface InnerInterface // ok
    {
        String CONST = "InnerInterface"; // ok
        void method(); // ok

        class InnerInnerClass // ok
        {
            private int mData; // ok

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

    private int mSize; // ok
    int mLen; // ok
    protected int mDeer; // ok
    public int aFreddo; // ok

    // ok
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
