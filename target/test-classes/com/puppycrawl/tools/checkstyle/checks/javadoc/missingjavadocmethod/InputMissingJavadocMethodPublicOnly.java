/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF , COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 * scope = "private"
 */
public class InputMissingJavadocMethodPublicOnly
{
    private interface InnerInterface
    {
        String CONST = "InnerInterface";
        void method(); // violation

        class InnerInnerClass
        {
            private int mData;

            private InnerInnerClass() // violation
            {
                final Runnable r = new Runnable() {
                        public void run() {};
                    };
            }

            void method2() // violation
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

        void method() // violation
        {
        }
    }

    private int mSize;
    int mLen;
    protected int mDeer;
    public int aFreddo;

    private InputMissingJavadocMethodPublicOnly(int aA) // violation
    {
    }

    InputMissingJavadocMethodPublicOnly(String aA) // violation
    {
    }

    protected InputMissingJavadocMethodPublicOnly(Object aA) // violation
    {
    }

    public InputMissingJavadocMethodPublicOnly(Class<Object> aA) // violation
    {
    }

    private void method(int aA) // violation
    {
    }

    void method(Long aA) // violation
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
