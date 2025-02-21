/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = protected
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodPublicOnly3
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

    private InputMissingJavadocMethodPublicOnly3(int aA)
    {
    }

    InputMissingJavadocMethodPublicOnly3(String aA)
    {
    }

    protected InputMissingJavadocMethodPublicOnly3(Object aA) // violation
    {
    }

    public InputMissingJavadocMethodPublicOnly3(Class<Object> aA) // violation
    {
    }

    private void method(int aA)
    {
    }

    void method(Long aA)
    {
    }

    protected void method(Class<Object> aA) // violation
    {
    }

    public void method(StringBuffer aA) // violation
    {
    }

    /**
     * A param tag should not be required here when relaxed about Javadoc.
     * Writing a little documentation should not be worse than not
     * writing any documentation at all.
     */
    private void method(String aA)
    {
    }

    /**
     * This inner class has no author tag, which is OK.
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
